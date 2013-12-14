package infrastructure.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StartFtpServer {

    public static void main(String[] args) throws Exception {

        File temporaryFileToDelete = File.createTempFile("the file", "txt");
        temporaryFileToDelete.delete();
        File tmpDirectory = temporaryFileToDelete.getParentFile();
        File temporaryFtpRoot = new File(tmpDirectory, "ftp");
        File userHome = new File(temporaryFtpRoot, "sally");
        userHome.mkdirs();

        StartFtpServer startFtpServer = new StartFtpServer();
        startFtpServer.runOnPort(10021);
        startFtpServer.addUser("sally", "secret", userHome);

        startFtpServer.start();
    }

    private final FtpServerFactory serverFactory = new FtpServerFactory();
    private final InMemoryUserManager userManager = new InMemoryUserManager();
    private final ListenerFactory listenerFactory = new ListenerFactory();

    private void runOnPort(int port) {
        listenerFactory.setPort(port);
    }

    private void addUser(String userName, String secret, File userHome) throws FtpException {
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setPassword(secret);
        user.setMaxIdleTime(0);
        user.setHomeDirectory(userHome.getAbsolutePath());
        user.setEnabled(true);

        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new ConcurrentLoginPermission(0, 0));
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);
        userManager.save(user);
    }

    private void start() throws FtpException {
        serverFactory.addListener("default", listenerFactory.createListener());
        serverFactory.setUserManager(userManager);
        FtpServer server = serverFactory.createServer();
        server.start();
    }
}
