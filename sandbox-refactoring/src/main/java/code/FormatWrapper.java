package code;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatWrapper extends SimpleDateFormat {

    public FormatWrapper(String format) {
        super(format);
    }

    public FormatWrapper(String format, Locale locale) {
        super(format, locale);
    }


    public Date myParse(String date) throws ParseException {
        return super.parse(date);
    }
}