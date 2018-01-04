package app.utils;

import org.eclipse.jetty.server.AbstractNCSARequestLog;
import org.slf4j.Logger;
import spark.embeddedserver.EmbeddedServers;
import spark.embeddedserver.jetty.EmbeddedJettyFactory;

public class SparkUtils {
    public static void createServerWithRequestLog(Logger logger) {
        EmbeddedJettyFactory factory = createEmbeddedJettyFactoryWithRequestLog(logger);
        EmbeddedServers.add(EmbeddedServers.Identifiers.JETTY, factory);
    }

    private static EmbeddedJettyFactory createEmbeddedJettyFactoryWithRequestLog(Logger logger) {
        AbstractNCSARequestLog requestLog = new RequestLogFactory(logger).create();
        return new EmbeddedJettyFactoryConstructor(requestLog).create();
    }
}
