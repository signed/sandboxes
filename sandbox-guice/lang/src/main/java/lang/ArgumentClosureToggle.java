package lang;

public class ArgumentClosureToggle<T> implements ArgumentClosure<T>{

    public static <T> ArgumentClosureToggle<T> toggleAround(ArgumentClosure<T> closure) {
        return new ArgumentClosureToggle<T>(closure);
    }

    private final ArgumentClosure<T> closure;
    private boolean activated;

    public ArgumentClosureToggle(ArgumentClosure<T> closure) {
        this.closure = closure;
        activate();
    }

    @Override
    public void excecute(T t) {
        if(activated){
            closure.excecute(t);
        }
    }

    public void suspend() {
        activated = false;
    }

    public void activate() {
        activated = true;
    }
}
