package java8.chapter_01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ForEach_Test {

    @Test
    void forEachOnIterable() {
        List<String> list = Arrays.asList("one", "two", "three");
        StringBuilder concat = new StringBuilder();

        list.forEach(concat::append);

        assertThat(concat.toString()).isEqualTo("onetwothree");
    }
}
