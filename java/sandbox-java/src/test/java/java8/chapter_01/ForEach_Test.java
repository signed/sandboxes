package java8.chapter_01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ForEach_Test {

    @Test
    void forEachOnIterable() throws Exception {
        List<String> list = Arrays.asList("one", "two", "three");
        StringBuilder concat = new StringBuilder();

        list.forEach(concat::append);

        assertThat(concat.toString(), is("onetwothree"));
    }
}
