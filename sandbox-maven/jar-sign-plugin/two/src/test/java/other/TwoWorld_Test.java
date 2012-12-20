package other;

import hallo.TestHelper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import other.TwoWorld;

import static org.hamcrest.MatcherAssert.assertThat;

public class TwoWorld_Test {

    @Test
    public void createTheWorld() throws Exception {
        new TestHelper();
        new TwoWorld();
        assertThat(new TwoWorld(), CoreMatchers.notNullValue());
    }
}
