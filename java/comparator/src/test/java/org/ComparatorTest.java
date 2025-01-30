package org;

import io.vavr.collection.Array;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ComparatorTest {

    record ToCompare(int age, String name){
        public static ToCompare create(int age, String name ){
            return new ToCompare(age, name);
        }
    }

    @Test
    void sortingBySingleProperty() {

        final var toSort = Arrays.asList(ToCompare.create(3, "c"), ToCompare.create(1, "a"), ToCompare.create(2, "b"));
        final var sorted = toSort.stream().sorted(Comparator.comparing(it -> it.age)).toList();

        assertThat(sorted).containsExactly(ToCompare.create(1, "a"), ToCompare.create(2, "b"), ToCompare.create(3, "c"));
    }
}
