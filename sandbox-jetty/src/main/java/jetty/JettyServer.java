package jetty;

import com.google.inject.servlet.GuiceFilter;
import jersey.guice.GuiceConfig;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {

    private static WebAppContext createContext(String contextPath, String contentDirectory) {
        WebAppContext webAppContext = new WebAppContext("webapp", contextPath);
        webAppContext.setResourceBase("server/src/main/" + contentDirectory);
        webAppContext.setDescriptor("WEB-INF/web.xml");
        return webAppContext;
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8182);

        ServletContextHandler jerseyServlet = new ServletContextHandler();
        jerseyServlet.setResourceBase("");
        jerseyServlet.addEventListener(new GuiceConfig());
        jerseyServlet.addFilter(GuiceFilter.class, "/*", null);
        jerseyServlet.addServlet(DefaultServlet.class, "/");
        jerseyServlet.setContextPath("/buggy");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{jerseyServlet});
        server.setHandler(contexts);

        server.start();
        server.join();
    }
}
