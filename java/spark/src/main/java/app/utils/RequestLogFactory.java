package app.utils;

import org.eclipse.jetty.server.AbstractNCSARequestLog;
import org.slf4j.Logger;

public class RequestLogFactory {

    private Logger logger;

    public RequestLogFactory(Logger logger) {
        this.logger = logger;
    }

    AbstractNCSARequestLog create() {
        return new AbstractNCSARequestLog() {
            @Override
            protected boolean isEnabled() {
                return true;
            }

            @Override
            public void write(String s) {
                logger.info(s);
            }
        };
    }
}
