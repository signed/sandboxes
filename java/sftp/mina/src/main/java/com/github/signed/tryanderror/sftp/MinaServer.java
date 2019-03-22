package com.github.signed.tryanderror.sftp;

import org.apache.log4j.PropertyConfigurator;
import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.keyprovider.ResourceKeyPairProvider;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.PublickeyAuthenticator;
import org.apache.sshd.server.filesystem.NativeFileSystemFactory;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.PublicKey;
import java.security.Security;
import java.util.Collections;

public class MinaServer {

    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        Security.addProvider(new BouncyCastleProvider());
        new MinaServer().start();
    }

    private void start() throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(2022);
        String[] strings = {"mina/hostkey.pem"};
        sshd.setKeyPairProvider(new ResourceKeyPairProvider(strings));
        sshd.setShellFactory(new SftpSubsystem.Factory());
        sshd.setSubsystemFactories(Collections.<NamedFactory<Command>>singletonList(new SftpSubsystem.Factory()));
        sshd.setPasswordAuthenticator(new LenientPasswordAuthenticator());
        sshd.setPublickeyAuthenticator(new LenientPublickeyAuthenticator());
        sshd.setFileSystemFactory(new NativeFileSystemFactory() );
        sshd.start();
    }

    private static class LenientPublickeyAuthenticator implements PublickeyAuthenticator {
        @Override
        public boolean authenticate(String username, PublicKey key, ServerSession session) {
            return true;
        }
    }

    private static class LenientPasswordAuthenticator implements PasswordAuthenticator {
        @Override
        public boolean authenticate(String username, String password, ServerSession session) {
            return true;
        }
    }
}
