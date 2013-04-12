package code;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeWithSimpleDateFormat {

    public static final SimpleDateFormat HourMinutesAndSeconds = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat notInitialized;

    public CodeWithSimpleDateFormat(SimpleDateFormat input) {
        this.notInitialized = input;
    }

    public String getDateAsString(Date date) {
        return HourMinutesAndSeconds.format(date);
    }

    public Date toDate(String timestamp) {
        try {
            return HourMinutesAndSeconds.parse(timestamp);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String dayOfWeek(Date date) {
        return new SimpleDateFormat("EEEE").format(date);
    }
}