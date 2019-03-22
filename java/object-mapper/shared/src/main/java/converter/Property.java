package converter;

public class Property {
    public final Object instance;
    public final Class<?> declaredType;

    public Property(Object instance, Class<?> declaredType) {
        this.instance = instance;
        this.declaredType = declaredType;
    }
}
