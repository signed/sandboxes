package org.example;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("tag-on-the-container")
public class ContainerWithNestedTests {

    @Nested
    class One {
        @Test
        void one_method() {

        }
    }

    @Nested
    class Two {
        @Test
        void two_method() {

        }
    }
}
