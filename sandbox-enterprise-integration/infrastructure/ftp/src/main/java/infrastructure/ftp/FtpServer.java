package infrastructure.ftp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class FtpServer {

    public static void main(String[] args) throws Exception {

        File temporaryFileToDelete = File.createTempFile("the file", "txt");
        temporaryFileToDelete.delete();
        File tmpDirectory = temporaryFileToDelete.getParentFile();
        File temporaryFtpRoot = new File(tmpDirectory, "ftp");
        FtpServer ftpServer = new FtpServer(temporaryFtpRoot);
        ftpServer.runOnPort(10021);
        ftpServer.addUser("sally", "secret");
        ftpServer.addUser("harry", "secret");

        System.out.println("ftp root: "+temporaryFtpRoot);

        ftpServer.start();
    }

    private static File createUserHome(File temporaryFtpRoot, String sally) {
        File userHome = new File(temporaryFtpRoot, sally);
        userHome.mkdirs();
        return userHome;
    }

    private final FtpServerFactory serverFactory = new FtpServerFactory();
    private final InMemoryUserManager userManager = new InMemoryUserManager();
    private final ListenerFactory listenerFactory = new ListenerFactory();
    private File temporaryFtpRoot;

    public FtpServer(File temporaryFtpRoot) {
        this.temporaryFtpRoot = temporaryFtpRoot;
    }

    private void runOnPort(int port) {
        listenerFactory.setPort(port);
    }

    private void addUser(String userName, String secret) throws FtpException {
        File userHome = createUserHome(temporaryFtpRoot, userName);
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setPassword(secret);
        user.setMaxIdleTime(0);
        user.setHomeDirectory(userHome.getAbsolutePath());
        user.setEnabled(true);

        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new ConcurrentLoginPermission(0, 0));
        authorities.add(new WritePermission());
        authorities.add(new TransferRatePermission(0, 0));
        user.setAuthorities(authorities);
        userManager.save(user);
    }

    private void start() throws FtpException {
        serverFactory.addListener("default", listenerFactory.createListener());
        serverFactory.setUserManager(userManager);
        org.apache.ftpserver.FtpServer server = serverFactory.createServer();
        server.start();
    }
}
