package java8.chapter_01.lamdas;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java8.chapter_01.lamdas.Primer.CreateWith;
import static org.assertj.core.api.Assertions.assertThat;

class Primer_Test {

    @Test
    void useStaticMethodAsComparator() {
        List<String> someStrings = new ArrayList<>();
        someStrings.add("b");
        someStrings.add("a");
        someStrings.add("c");

        someStrings.sort(Primer::compareStrings);

        assertThat(someStrings).isEqualTo(asList("a", "b", "c"));
    }

    @Test
    void useAMethodOfOneOfTheInstances() {
        List<Primer> somePrimers = new ArrayList<>();
        somePrimers.add(CreateWith("a"));
        somePrimers.add(CreateWith("d"));
        somePrimers.add(CreateWith("c"));

        somePrimers.sort(Primer::someCompareMethod);

        assertThat(somePrimers).isEqualTo(asList(CreateWith("a"), CreateWith("c"), CreateWith("d")));
    }
}