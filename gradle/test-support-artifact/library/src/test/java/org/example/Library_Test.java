package org.example;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Library_Test {

    @Test
    public void testName() throws Exception {
        Library library = new Library();
        Example rain = ExampleBuilder.name("rain").contains("gravity in action").createdToday().build();
        library.add(rain);

        assertThat(library.contains(rain), is(true));
    }
}
