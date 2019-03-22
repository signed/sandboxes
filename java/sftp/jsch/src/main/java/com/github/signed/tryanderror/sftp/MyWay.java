package com.github.signed.tryanderror.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Vector;

public class MyWay {

    public static void main(String[] args) throws Exception {
        new MyWay().run();
    }

    private void run() throws Exception {
        JSch.setConfig("StrictHostKeyChecking", "yes");
        JSch.setConfig("HashKnownHosts", "no");
        JSch jsch = new JSch();

        InputStream inputStream = publicKeyForLocalhostAtPort2022();
        jsch.setKnownHosts(inputStream);
        Session session = jsch.getSession("signed", "localhost", 2022);
        session.setUserInfo(new MyUserInfo());
        session.connect();

        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        dumpContentOfCurrentWorkingDirectory(channel);
        channel.disconnect();
        session.disconnect();
    }

    private ByteArrayInputStream publicKeyForLocalhostAtPort2022() {
        String line = new KnownHost("localhost", 2022, ServerDsaKeyFactory.createTheServerkey()).toLine();
        return new ByteArrayInputStream(line.getBytes());
    }

    private void dumpContentOfCurrentWorkingDirectory(ChannelSftp channel) {
        try {
            Vector response = channel.ls("./");
            if (response != null) {
                for (int ii = 0; ii < response.size(); ii++) {

                    Object obj = response.elementAt(ii);
                    if (obj instanceof ChannelSftp.LsEntry) {
                        System.out.println(((ChannelSftp.LsEntry) obj).getLongname());
                    }

                }
            }
        } catch (SftpException e) {
            System.out.println(e.toString());
        }
    }

    private static class MyUserInfo implements UserInfo {
        @Override
        public String getPassphrase() {
            System.out.println("get passphrase:");
            return "";
        }

        @Override
        public String getPassword() {
            System.out.println("get password:");
            return "";
        }

        @Override
        public boolean promptPassword(String message) {
            System.out.println("prompt password:" + message);
            return true;
        }

        @Override
        public boolean promptPassphrase(String message) {
            System.out.println("prompt passphrase:"+message);
            return true;
        }

        @Override
        public boolean promptYesNo(String message) {
            System.out.println("prompt yes/no:"+message);
            return true;
        }

        @Override
        public void showMessage(String message) {
            System.out.println("show message:" +message);
        }
    }

}
