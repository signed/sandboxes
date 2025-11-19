package handcrafted;

import org.DomainObject;
import org.assertj.core.api.AbstractObjectAssert;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class HandcraftedDomainObjectAssert extends AbstractObjectAssert<HandcraftedDomainObjectAssert, DomainObject> {

    public HandcraftedDomainObjectAssert(DomainObject actual) {
        super(actual, HandcraftedDomainObjectAssert.class);
    }

    @org.assertj.core.annotation.CheckReturnValue
    public static HandcraftedDomainObjectAssert assertThat(DomainObject actual) {
        return new HandcraftedDomainObjectAssert(actual);
    }

    record Flup(String fieldName, String expectedString) {
    }

    public HandcraftedDomainObjectAssert flup(String s) {
        isNotNull();
        var lines = s.split("\n");
        var flups = Arrays.stream(lines).map(line -> {
            var parts = line.split(": ");
            return new Flup(parts[0], parts[1]);
        });

        flups.forEach(flup -> {
            String actual = this.actual.name;
            String expected = flup.expectedString;
            String assertjErrorMessage = "\nExpecting name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";
            if (!Objects.deepEquals(actual, expected)) {
                failWithMessage(assertjErrorMessage, this.actual, expected, actual);
            }
        });

        return this;
    }
}
