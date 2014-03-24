package javassist;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.Method;

public class JavasisstHelper {
    public static <T> T create(Class<T> classs) throws Exception {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(classs);
        Class clazz = factory.createClass();
        MethodHandler handler = new MethodHandler() {
            private String value;
            @Override
            public Object invoke(Object self, Method overridden, Method forwarder, Object[] args) throws Throwable {
                System.out.println("hit decorator");
                return forwarder.invoke(self, args);
            }
        };
        Object instance = clazz.newInstance();
        ((ProxyObject) instance).setHandler(handler);
        return (T) instance;
    }
}
