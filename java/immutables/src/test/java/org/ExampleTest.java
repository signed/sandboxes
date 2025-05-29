package org;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleTest {
    @Test
    void example() {
        var build = ImmutableFoobarValue.builder().foo(42).bar("hello").build();

        assertThat(build.bar()).isEqualTo("hello");
    }
}
