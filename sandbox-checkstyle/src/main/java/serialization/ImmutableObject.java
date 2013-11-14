package serialization;

import java.io.Serializable;

public class ImmutableObject implements Serializable {
    private final String stuff;

    public ImmutableObject(String stuff) {
        this.stuff = stuff;
    }

    public String getStuff() {
        return this.stuff;
    }
}