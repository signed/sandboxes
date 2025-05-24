package records.constructor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompactConstructorTest {

    @Test
    void customizationOptions() {
        var log = new Log();
        var record = new CompactConstructor("welt", log);
        record.value();
        assertThat(log.lines()).containsExactly("compact constructor", "access value");
    }
}