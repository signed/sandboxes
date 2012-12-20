package jetty;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.util.EventListener;

public class DecoratorAdapter implements ServletContextHandler.Decorator {
    @Override
    public <T extends Filter> T decorateFilterInstance(T filter) throws ServletException {
        return filter;
    }

    @Override
    public <T extends Servlet> T decorateServletInstance(T servlet) throws ServletException {
        return servlet;
    }

    @Override
    public <T extends EventListener> T decorateListenerInstance(T listener) throws ServletException {
        return listener;
    }

    @Override
    public void decorateFilterHolder(FilterHolder filter) throws ServletException {

    }

    @Override
    public void decorateServletHolder(ServletHolder servlet) throws ServletException {

    }

    @Override
    public void destroyServletInstance(Servlet s) {

    }

    @Override
    public void destroyFilterInstance(Filter f) {

    }

    @Override
    public void destroyListenerInstance(EventListener f) {

    }
}
