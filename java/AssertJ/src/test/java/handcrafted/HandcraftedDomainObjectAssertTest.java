package handcrafted;

import org.DomainObject;
import org.DomainObjectAssert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandcraftedDomainObjectAssertTest {

    @Test
    void funky() {
        var actual = new DomainObject();
        actual.name = "expected";

        HandcraftedDomainObjectAssert.assertThat(actual).flup("""
                name: expected
                """);
    }

    @Test
    void OtherWayRoundFunky() {
        var actual = new DomainObject();
        actual.name = "expected";
        actual.age = 42;

        HandcraftedDomainObjectAssert.assertThat(actual).flap("""
                name: expected
                """);
    }
}