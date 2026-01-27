package org.example;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TckUsageTest {
    @Test
    void example() {
        assertThat(Optional.empty()).isEmpty();
    }
}
