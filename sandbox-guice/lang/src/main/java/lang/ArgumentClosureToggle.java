package lang;

public class ArgumentClosureToggle<T> implements ArgumentClosure<T>{

    public static <T> ArgumentClosureToggle<T> toggleAround(ArgumentClosure<T> closure) {
        return new ArgumentClosureToggle<>(closure);
    }

    private final ArgumentClosure<T> closure;
    private boolean activated;

    public ArgumentClosureToggle(ArgumentClosure<T> closure) {
        this.closure = closure;
        activate();
    }

    @Override
    public void execute(T t) {
        if(activated){
            closure.execute(t);
        }
    }

    public void suspend() {
        activated = false;
    }

    public void activate() {
        activated = true;
    }
}
