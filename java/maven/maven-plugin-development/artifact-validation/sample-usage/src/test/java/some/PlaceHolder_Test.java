package some;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlaceHolder_Test {
    @Test
    public void aDummyTest() throws Exception {
        assertThat(new PlaceHolder(), notNullValue());
    }

    @Test
    @Ignore
    public void fail() throws Exception {
        assertThat(true, is(false));
    }
}
