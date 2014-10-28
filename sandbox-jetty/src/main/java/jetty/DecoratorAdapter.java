package jetty;

import org.eclipse.jetty.servlet.ServletContextHandler;

public class DecoratorAdapter implements ServletContextHandler.Decorator {
    @Override
    public <T> T decorate(T t) {
        return null;
    }

    @Override
    public void destroy(Object o) {

    }
}
