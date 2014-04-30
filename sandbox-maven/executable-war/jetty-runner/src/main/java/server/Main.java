package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;

public class Main {
    private final BinaryRoot binaryRoot;

    public static void main(String[] args) throws Exception {
        URL binariesLocation = Main.class.getProtectionDomain().getCodeSource().getLocation();
        BinaryRoot root;
        if(binariesLocation.toExternalForm().endsWith(".jar")) {
            root = new JarRoot(binariesLocation);
        }else{
            root = new TargetClassesRoot(binariesLocation);
        }

        new Main(root).run();
    }

    public Main(BinaryRoot root) {
        this.binaryRoot = root;
    }

    private void run() {
        Server server = new Server(8182);
        WebAppContext root = new WebAppContext();
        //root.setDescriptor(binaryRoot.get("WEB-INF/web.xml").toExternalForm());
        //root.setResourceBase(binaryRoot.getRoot().toExternalForm());
        root.setWar(binaryRoot.getRoot().toExternalForm());
        //root.setContextPath("/down");
        root.setParentLoaderPriority(true);

        server.setHandler(root);

        try {
            server.start();
            server.join();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(100);
        }
    }
}