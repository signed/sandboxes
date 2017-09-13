package org.example;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Example_Test {

    @Test
    public void rememberName() throws Exception {
        Example example = new ExampleBuilder().example("Example 42").contains("the description of the universe").createdToday().build();
        assertThat(example.name(), is("Example 42"));
    }
}
