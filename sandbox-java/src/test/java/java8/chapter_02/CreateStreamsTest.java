package java8.chapter_02;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

public class CreateStreamsTest {
    @Test
    public void fromArrays() throws Exception {
        String [] stringArray = {"one", "two", "three"};
        Stream<String> stream = Arrays.stream(stringArray);
    }


}
