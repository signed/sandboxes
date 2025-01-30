package org;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleTest {

    @Test
    void name() {
        assertThat(Optional.empty()).isEmpty();
    }
}
