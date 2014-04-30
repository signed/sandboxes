package tryanderror.xstream;

import static org.hamcrest.CoreMatchers.is;
import static org.joda.time.DateTimeZone.UTC;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.github.signed.xml.xstream.domain.DateTimeConverter;

public class DateTimeConverter_Test {
    private final DateTimeConverter converter = new DateTimeConverter("v");
    private final DateTimeZone localTimeZone = new DateTime().getZone();
    
    @Test
    public void convertsDateTimeObjects() {
        assertThat(converter.canConvert(DateTime.class), is(true));
    }

    @Test
    public void doesNotConvertAnyOtherClass() {
        Class<String> anyOtherType = String.class;
        assertThat(converter.canConvert(anyOtherType), is(false));
    }

    @Test
    public void convertsToUtcAndStripsMilliseconds() throws Exception {
        DateTime dateTime = new DateTime(2005, 10, 3, 11, 14, 43, 47, DateTimeZone.UTC);
        DateTime inLocalTimeZone = dateTime.withZone(localTimeZone);
        assertThat(converter.toString(inLocalTimeZone), is("2005-10-03T11:14:43Z"));
    }

    @Test
    public void convertsAStringInEntsoFormatToDateTimeInUtc() throws Exception {
        assertThat(converter.fromString("2011-04-01T17:15:23Z"), is(new DateTime(2011, 4, 1, 17, 15, 23, UTC)));
    }
}