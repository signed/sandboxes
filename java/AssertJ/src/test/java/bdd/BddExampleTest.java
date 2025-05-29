package bdd;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.and;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenComparable;

@SuppressWarnings("AccessStaticViaInstance")
public class BddExampleTest {

    @Test
    void name() {
        then("hello").isEqualTo("hello");
        and.then("resolve then naming conflicts with other libraries").isEqualTo("resolve then naming conflicts with other libraries");
        thenComparable(BigDecimal.TWO).isGreaterThan(BigDecimal.ZERO);
    }
}
