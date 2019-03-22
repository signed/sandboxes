package lang;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


public class Announcer<T> {

    public static <T> Announcer<T> to(Class<? extends T> listenerType) {
        return new Announcer<T>(listenerType);
    }

    private final T proxy;

    private final List<T> listeners = new ArrayList<T>();

    public Announcer(Class<? extends T> listenerType) {
        proxy = listenerType.cast(Proxy.newProxyInstance(
                listenerType.getClassLoader(),
                new Class<?>[]{listenerType},
                new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        announce(method, args);
                        return null;
                    }
                }));
    }

    public void addListener(T listener) {
        listeners.add(listener);
    }

    public T announce() {
        return proxy;
    }

    private void announce(Method m, Object[] args) {
        try {
            List<T> copy;
            synchronized (this) {
                copy = new ArrayList<T>(listeners);
            }
            for (T listener : copy) {
                m.invoke(listener, args);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("could not invoke listener", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw new UnsupportedOperationException("listener threw exception", cause);
            }
        }
    }
}
