package application.recordings.view;

import domain.Recording;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class AirDateCell extends SafeTableCell<Recording, DateTime> {
    private final DateTimeFormatter format = new DateTimeFormatterBuilder().appendDayOfMonth(2).appendLiteral(".").appendMonthOfYear(2).appendLiteral(".").appendYear(4, 4).toFormatter();
    @Override
    protected void updateSafely(DateTime dateTime) {
        setText(format.print(dateTime));
    }
}
