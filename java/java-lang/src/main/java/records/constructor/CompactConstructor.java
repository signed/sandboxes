package records.constructor;

import java.util.Objects;

public record CompactConstructor(String value, Log log) {
    public CompactConstructor {
        Objects.requireNonNull(log);
        Objects.requireNonNull(value);
        log.add("compact constructor");
    }

    public String value(){
        log.add("access value");
        return value;
    }

}
