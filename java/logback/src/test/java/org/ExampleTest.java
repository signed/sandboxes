package org;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleTest {
    @Test
    void example() {
        assertThat(Optional.empty()).isEmpty();
    }

    @Test
    void loggingJsonStrings() {
        var logger = LoggerFactory.getLogger("Hello Logger");
        logger.info("\"Hello\"");
    }

    @Test
    void loggingDto() {
        record NestedDto(String chicken) {}

        record LoggingDto(String key, String property, NestedDto nested) {
        }

        var logger = LoggerFactory.getLogger("Hello Logger");
        var dto = new LoggingDto("one", "two", new NestedDto("egg"));
        logger.atInfo().addKeyValue("user_info", dto).log();
    }
}
