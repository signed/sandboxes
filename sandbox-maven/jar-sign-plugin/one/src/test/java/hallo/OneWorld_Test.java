package hallo;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class OneWorld_Test {

    @Test
    public void createTheWorld() throws Exception {
        assertThat(new OneWorld(), CoreMatchers.notNullValue());
    }
}
