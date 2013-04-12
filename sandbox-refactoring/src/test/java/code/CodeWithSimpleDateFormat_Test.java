package code;

import org.joda.time.DateTime;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CodeWithSimpleDateFormat_Test {
    private final SimpleDateFormat input = new SimpleDateFormat("dd:yyyy");
    private final CodeWithSimpleDateFormat code = new CodeWithSimpleDateFormat(input);

    @Test
    public void callTheConstructor() throws Exception {
        String dateAsString = code.getDateAsString(new Date(0));
        assertThat(dateAsString, is("01:00:00"));
    }

    @Test
    public void callTheMethod() throws Exception {
        Date date = code.toDate("16:30:14");
        assertThat(date, is(new DateTime(1970, 1, 1, 16, 30, 14).toDate()));
    }

    @Test
    public void anotherMethod() throws Exception {
        assertThat(code.dayOfWeek(new Date(0)), is("Thursday"));
    }
}
