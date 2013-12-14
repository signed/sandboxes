package infrastructure.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;

public class StartFtpServer {

    public static void main(String[] args) throws Exception {
        StartFtpServer startFtpServer = new StartFtpServer();
        startFtpServer.runOnPort(10021);
        startFtpServer.start();
    }

    private final FtpServerFactory serverFactory = new FtpServerFactory();

    private void runOnPort(int port) {
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(port);
        serverFactory.addListener("default", listenerFactory.createListener());
    }

    private void start() throws FtpException {
        FtpServer server = serverFactory.createServer();
        server.start();
    }
}
