package infrastructure.sftp;

import java.io.File;
import java.io.IOException;

import org.apache.sshd.common.Session;
import org.apache.sshd.common.file.FileSystemFactory;
import org.apache.sshd.common.file.FileSystemView;

public class TestFileSystemFactory implements FileSystemFactory {
    private File root;

    public TestFileSystemFactory(File root) {
        this.root = root;
    }

    @Override
    public FileSystemView createFileSystemView(Session session) throws IOException {
        return new TestFileSystemView(session, root);
    }
}