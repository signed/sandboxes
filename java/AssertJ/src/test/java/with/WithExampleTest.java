package with;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

/**
 * No static imports needed, just implement the interface
 * <a href="https://blog.javabien.net/2014/04/23/what-if-assertj-used-java-8/">...</a>
 */
public class WithExampleTest implements WithAssertions {

    @Test
    void name() {
        assertThat("Hello World").isEqualTo("Hello World");
    }
}
