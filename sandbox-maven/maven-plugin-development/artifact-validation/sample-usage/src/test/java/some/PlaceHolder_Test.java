package some;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class PlaceHolder_Test {
    @Test
    public void aDummyTest() throws Exception {
        assertThat(new PlaceHolder(), CoreMatchers.notNullValue());
    }
}
