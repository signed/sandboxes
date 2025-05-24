package java8.chapter_01.lamdas;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Primer_Test {

    @Test
    public void useStaticMethodAsComparator() throws Exception {
        List<String> someStrings = new ArrayList<>();
        someStrings.add("b");
        someStrings.add("a");
        someStrings.add("c");

        Collections.sort(someStrings, Primer::compareStrings);

        assertThat(someStrings, is(Arrays.asList("a", "b", "c")));
    }

    @Test
    public void useAMethodOfOneOfTheInstances() throws Exception {
        List<Primer> somePrimers = new ArrayList<>();
        somePrimers.add(Primer.CreateWith("a"));
        somePrimers.add(Primer.CreateWith("d"));
        somePrimers.add(Primer.CreateWith("c"));

        Collections.sort(somePrimers, Primer::someCompareMethod);

        assertThat(somePrimers, is(Arrays.asList(Primer.CreateWith("a"), Primer.CreateWith("c"), Primer.CreateWith("d"))));
    }
}