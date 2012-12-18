package jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

    private static WebAppContext createContext(String contextPath, String contentDirectory) {
        WebAppContext webAppContext = new WebAppContext("webapp", contextPath);
        webAppContext.setResourceBase("server/src/main/" + contentDirectory);
        webAppContext.setDescriptor("src/main/resources/WEB-INF/web.xml");
        return webAppContext;
    }

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
        server.join();
    }
}
