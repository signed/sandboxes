package clients;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

public class InfluxWriter {

    private static Random random = new Random(42L);

    public static void main(String[] args) {
        String dockerIp = new Docker().dockerIp();
        InfluxDB influxDB = InfluxDBFactory.connect("http://" + dockerIp + ":8086", "root", "root");
        String dbName = "aTimeSeries";
        influxDB.createDatabase(dbName);

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy("autogen")
                .consistency(ConsistencyLevel.ALL)
                .build();

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        ZonedDateTime startOfDayToday = today.atStartOfDay(ZoneId.of("UTC"));

        int maxValue = 250;
        int direction = 1;
        int value = 0;

        for (ZonedDateTime timestamp = startOfDayToday; timestamp.isBefore(tomorrow.atStartOfDay(ZoneId.of("UTC"))); timestamp = timestamp.plusSeconds(10)) {
            if (Math.abs(value) >= maxValue) {
                direction = direction * -1;
            }

            value = value + (direction * random(0, 10));
            Point point = Point.measurement("cpu")
                    .time(timestamp.toInstant().toEpochMilli(), TimeUnit.MILLISECONDS)
                    .addField("fump", value)
                    .build();
            batchPoints.point(point);
        }

        influxDB.write(batchPoints);
    }

    public static int random(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
