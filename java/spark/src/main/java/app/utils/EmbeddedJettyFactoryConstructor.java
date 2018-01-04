package app.utils;

import org.eclipse.jetty.server.AbstractNCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import spark.embeddedserver.jetty.EmbeddedJettyFactory;
import spark.embeddedserver.jetty.JettyServerFactory;

public class EmbeddedJettyFactoryConstructor {
    private final AbstractNCSARequestLog requestLog;

    public EmbeddedJettyFactoryConstructor(AbstractNCSARequestLog requestLog) {
        this.requestLog = requestLog;
    }

    EmbeddedJettyFactory create() {


		return new EmbeddedJettyFactory(new JettyServerFactory() {
			@Override
			public Server create(int maxThreads, int minThreads, int threadTimeoutMillis) {
				Server server;

				if (maxThreads > 0) {
					int max = maxThreads;
					int min = (minThreads > 0) ? minThreads : 8;
					int idleTimeout = (threadTimeoutMillis > 0) ? threadTimeoutMillis : 60000;

					server = new Server(new QueuedThreadPool(max, min, idleTimeout));
				} else {
					server = new Server();
				}
				server.setRequestLog(requestLog);
				return server;
			}

			@Override
			public Server create(ThreadPool threadPool) {
				return threadPool != null ? new Server(threadPool) : new Server();
			}
		});
    }

}
