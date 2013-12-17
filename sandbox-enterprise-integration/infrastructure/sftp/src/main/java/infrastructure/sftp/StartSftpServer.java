package infrastructure.sftp;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.PublickeyAuthenticator;
import org.apache.sshd.server.UserAuth;
import org.apache.sshd.server.auth.UserAuthPassword;
import org.apache.sshd.server.auth.UserAuthPublicKey;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem;

public class StartSftpServer {

    public static void main(String [] args ) throws Exception{
        final SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(10022);
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));

        File serverRoot = new File("/tmp/sftp/");
        serverRoot.mkdirs();
        sshd.setFileSystemFactory(new TestFileSystemFactory(serverRoot));

        List<NamedFactory<UserAuth>> userAuthFactories = new ArrayList<NamedFactory<UserAuth>>();
        userAuthFactories.add(new UserAuthPassword.Factory());
        userAuthFactories.add(new UserAuthPublicKey.Factory());
        sshd.setUserAuthFactories(userAuthFactories);

        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
            public boolean authenticate(String username, String password, ServerSession session) {
                return "signed".equals(username) && "secret".equals(password);
            }
        });

        sshd.setPublickeyAuthenticator(new PublickeyAuthenticator() {
            @Override
            public boolean authenticate(String username, PublicKey key, ServerSession session) {
                return true;
            }
        });

        sshd.setCommandFactory(new ScpCommandFactory());

        List<NamedFactory<Command>> namedFactoryList = new ArrayList<NamedFactory<Command>>();
        namedFactoryList.add(new SftpSubsystem.Factory());
        sshd.setSubsystemFactories(namedFactoryList);
        try {
            sshd.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(Long.MAX_VALUE);
    }
}
