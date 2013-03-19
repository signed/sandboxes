package serialization;

public class ImmutableObject {
    private final String stuff;

    public ImmutableObject(String stuff) {
        this.stuff = stuff;
    }


    public String getStuff() {
        return this.stuff;
    }
}