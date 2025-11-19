package handcrafted;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class HandcraftedDomainObjectAssertTest {

    @Test
    void funky() {
        var actual = new AnotherDomainObject();
        actual.name = "expected";

        HandcraftedDomainObjectAssert.assertThat(actual).flup("""
                name: expected
                """);
    }

    @Test
    void OtherWayRoundFunky() {
        var actual = new AnotherDomainObject();
        actual.name = "expected";
        actual.age = 42;
        actual.salary = BigDecimal.valueOf(50);

        HandcraftedDomainObjectAssert.assertThat(actual).flap("""
                name: expected
                age: 42
                salary: 50
                """);
    }

    @Test
    void compareMultiLineStrings() {
        Assertions.assertThat("""
                hello
                to
                you
                too
                """).isEqualTo("""
                hello
                to
                you
                too
                """);
    }
}