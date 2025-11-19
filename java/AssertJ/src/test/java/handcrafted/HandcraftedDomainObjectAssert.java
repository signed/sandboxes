package handcrafted;

import org.DomainObject;
import org.DomainObjectAssert;
import org.assertj.core.api.AbstractObjectAssert;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
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

    public HandcraftedDomainObjectAssert flap(String expected) {
        isNotNull();
        var lines = expected.split("\n");
        var flups = Arrays.stream(lines).map(line -> {
            var parts = line.split(": ");
            return new Flup(parts[0], parts[1]);
        });

        var actual = flups.map(it -> {
            var field = it.fieldName;
            if ("name".equals(field)) {
                return field + ": " + this.actual.name;
            }
            throw new RuntimeException("not implemented yet");
        }).collect(Collectors.joining("\n"));

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        if (!Objects.deepEquals(actual+"\n", expected)) {
            failWithMessage(assertjErrorMessage, actual, expected, actual);
        }

        return this;
    }
}
