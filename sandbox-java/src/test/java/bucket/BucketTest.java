package bucket;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class BucketTest {

    @Test
    public void sample() throws Exception {
        Instant now = Instant.now();
        Instant earlier = now.minus(20, ChronoUnit.MINUTES);
        List<Event> events = Arrays.asList(new Event(now, "now"), new Event(earlier, "earlier"));
        List<Event> sortedEvents = events.stream().sorted(byTimeStamp()).collect(Collectors.toList());
        Duration samplingRate = Duration.ofMinutes(1);
        Duration inspectionRange = Duration.ofMinutes(10);

        Instant earliest = sortedEvents.get(0).timestamp;
        Instant latest = sortedEvents.get(sortedEvents.size() - 1).timestamp;

        BucketAssemblyLine bucketAssemblyLine = new BucketAssemblyLine(samplingRate, inspectionRange, earlier);


    }

    @Test
    public void tinker() throws Exception {
        ArrayBlockingQueue<String> strings = new ArrayBlockingQueue<String>(2, false, new ArrayList<>());

        strings.add("one");
        strings.add("two");
        if (0 == strings.remainingCapacity()) {
            strings.remove();
        }
        strings.add("three");

        System.out.println(strings);
    }

    private Comparator<Event> byTimeStamp() {
        return new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.timestamp.compareTo(o2.timestamp);
            }
        };
    }

    public static class Interval {
        public final Instant start;
        public final Instant end;

        public Interval(Instant start, Instant end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class BucketAssemblyLine {
        private final List<Bucket> allBuckets = new LinkedList<>();
        private final ArrayBlockingQueue<Bucket> assemblyLine;
        private final Duration samplingRate;
        private final Duration inspectionRange;
        private Instant lastCreatedBucketLatest;

        public BucketAssemblyLine(Duration samplingRate, Duration inspectionRange, Instant earliest) {
            this.samplingRate = samplingRate;
            this.inspectionRange = inspectionRange;

            Instant stop = earliest.plus(inspectionRange);
            Instant current = earliest;

            while (current.isBefore(stop)) {
                lastCreatedBucketLatest = current.plus(inspectionRange);
                allBuckets.add(new Bucket(lastCreatedBucketLatest, inspectionRange));

                current = current.plus(samplingRate);
            }
            assemblyLine = new ArrayBlockingQueue<>(allBuckets.size(), false, allBuckets);
        }

        public void putIntoBucket(Event event) {
            if (!event.timestamp.isBefore(assemblyLine.peek().latest())) {
                assemblyLine.remove();
                lastCreatedBucketLatest = lastCreatedBucketLatest.plus(samplingRate);
                Bucket newBucket = new Bucket(lastCreatedBucketLatest, inspectionRange);
                assemblyLine.add(newBucket);
                allBuckets.add(newBucket);
            }

            assemblyLine.forEach(bucket -> bucket.put(event));
        }

        public List<Bucket> allBuckets() {
            return allBuckets;
        }
    }

    public static class Bucket {

        private final Instant latest;
        private final Duration inspectionRange;

        private final List<Event> events = new LinkedList<>();

        public Bucket(Instant latest, Duration inspectionRange) {
            this.latest = latest;
            this.inspectionRange = inspectionRange;
        }

        public Instant earliest() {
            return latest.minus(inspectionRange);
        }

        public Instant latest() {
            return latest;
        }

        public boolean put(Event event) {
            if (!event.timestamp.isBefore(latest)) {
                return false;
            }
            if (earliest().isAfter(event.timestamp)) {
                return false;
            }
            return this.events.add(event);
        }

        public List<Event> items() {
            return events;
        }
    }

    public static class Event {
        public final Instant timestamp;
        public final String data;

        public Event(Instant timestamp, String data) {
            this.timestamp = timestamp;
            this.data = data;
        }
    }
}
