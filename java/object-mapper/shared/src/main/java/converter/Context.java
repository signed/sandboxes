package converter;

public class Context {

    public final Property source;
    public final Property destination;

    public Context(Property source, Property destination) {
        this.source = source;
        this.destination = destination;
    }
}
