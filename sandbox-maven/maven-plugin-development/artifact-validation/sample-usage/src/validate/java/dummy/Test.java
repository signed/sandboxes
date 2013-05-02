package dummy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Test {

    @org.junit.Test
    public void fail() throws Exception {
        assertThat(true, is(false));
    }
}
