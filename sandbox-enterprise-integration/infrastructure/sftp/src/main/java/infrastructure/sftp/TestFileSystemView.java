package infrastructure.sftp;

import java.io.File;

import org.apache.sshd.common.Session;
import org.apache.sshd.common.file.SshFile;
import org.apache.sshd.common.file.nativefs.NativeFileSystemView;

public class TestFileSystemView extends NativeFileSystemView {

    private final Session session;
    private final File root;

    public TestFileSystemView(Session session, File root) {
        super(session.getUsername(), false);
        this.session = session;
        this.root = root;
    }

    @Override
    public SshFile getFile(String name) {
        if (name.startsWith("/")){
		    File file = new File(name);
		    return new TestSshFile(this, file, session.getUsername());
		}
		File file = new File(root, name);
		return new TestSshFile(this, file, session.getUsername());
    }

    @Override
    public SshFile getFile(SshFile sshFile, String name) {
        File file = new File(sshFile.getAbsolutePath(), name);
        return new TestSshFile(this, file, session.getUsername());
    }
}