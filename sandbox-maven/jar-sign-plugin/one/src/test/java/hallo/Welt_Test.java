package hallo;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class Welt_Test {

    @Test
    public void createTheWorld() throws Exception {
        assertThat(new Welt(), CoreMatchers.notNullValue());
    }
}
