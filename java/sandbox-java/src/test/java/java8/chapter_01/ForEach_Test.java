package java8.chapter_01;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ForEach_Test {

    @Test
    public void forEachOnIterable() throws Exception {
        List<String> list = Arrays.asList("one", "two", "three");
        StringBuilder concat = new StringBuilder();

        list.forEach(concat::append);

        assertThat(concat.toString(), is("onetwothree"));
    }
}
