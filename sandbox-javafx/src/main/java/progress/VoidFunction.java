package progress;

import com.google.common.base.Function;

public abstract class VoidFunction<T> implements Function<T, Void> {

    @Override
    public Void apply( T input) {
        applyTo(input);
        return null;
    }

    protected abstract void applyTo(T input);

}
