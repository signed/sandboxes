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
}