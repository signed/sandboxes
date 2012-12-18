package jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.EventListener;

public class JettyServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8182);

        String contentRoot = JettyServer.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
        System.out.println(contentRoot);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setServer(server);
        webapp.setWar(contentRoot);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{webapp});
        server.setHandler(contexts);

        server.start();
        dumpListeners(webapp);


        server.join();
    }

    private static void dumpListeners(WebAppContext webapp) {
        EventListener[] eventListeners = webapp.getEventListeners();

        for (EventListener eventListener : eventListeners) {
            System.out.println(eventListener.getClass());
        }
    }
}
