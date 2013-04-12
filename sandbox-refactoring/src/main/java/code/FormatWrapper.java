package code;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormatWrapper extends SimpleDateFormat {

    public FormatWrapper(String format) {
        super(format);
    }

    public FormatWrapper(String format, Locale locale) {
        super(format, locale);
    }
}