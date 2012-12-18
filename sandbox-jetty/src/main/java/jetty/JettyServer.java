package jetty;

import jersey.guice.GuiceConfig;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.ServletException;
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


        webapp.addDecorator(new DecoratorAdapter(){
            @Override
            public <T extends EventListener> T decorateListenerInstance(T listener) throws ServletException {
                if (listener instanceof GuiceConfig) {
                    GuiceConfig guiceConfig = (GuiceConfig) listener;
                    guiceConfig.swapLogic();
                }

                return listener;
            }
        });

        server.start();
        server.join();
    }

}
