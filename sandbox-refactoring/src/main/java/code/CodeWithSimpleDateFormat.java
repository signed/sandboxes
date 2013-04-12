package code;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CodeWithSimpleDateFormat {

    public static final SimpleDateFormat HourMinutesAndSeconds = new SimpleDateFormat("HH:mm:ss");
    private static final String Format = "HH";
    private final SimpleDateFormat notInitialized;
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");


    public CodeWithSimpleDateFormat(SimpleDateFormat input) {
        this.notInitialized = input;
        new SimpleDateFormat(Format);
        new SimpleDateFormat(Format, Locale.GERMANY);
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