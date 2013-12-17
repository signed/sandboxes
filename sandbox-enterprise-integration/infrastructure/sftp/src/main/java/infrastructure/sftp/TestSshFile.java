package infrastructure.sftp;

import java.io.File;

import org.apache.sshd.common.file.nativefs.NativeSshFile;


public class TestSshFile extends NativeSshFile {

    public TestSshFile(TestFileSystemView testFileSystemView, File file, String userName) {
        super(testFileSystemView, getAbsoluteLinuxPath(file), file, userName);
    }

    private static String getAbsoluteLinuxPath(File file) {
        StringBuilder builder = new StringBuilder();
        String original = file.getAbsolutePath();
        if (!original.startsWith("/")) {
            builder.append("/");
        }
        String linuxified = original.replaceAll("\\\\", "/");
        builder.append(linuxified);
        if (file.isDirectory()){
            builder.append("/");
        }
        if (builder.toString().endsWith(":")){
            builder.append("/");
        }
        return builder.toString();
    }
}