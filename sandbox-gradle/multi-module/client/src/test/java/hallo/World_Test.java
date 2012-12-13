package hallo;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class World_Test {

    @Test
    public void createTheWorld() throws Exception {
        assertThat(new World(), CoreMatchers.notNullValue());
    }

    @Test
    public void returnsExpectedString() throws Exception {
        assertThat(new World().someMethod(), CoreMatchers.is("tew"));
    }
}
