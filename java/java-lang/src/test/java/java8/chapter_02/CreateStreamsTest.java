package java8.chapter_02;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

class CreateStreamsTest {
    @Test
    void fromArrays() throws Exception {
        String [] stringArray = {"one", "two", "three"};
        Stream<String> stream = Arrays.stream(stringArray);
    }


}
