package funbugs;

import java.io.File;
import java.io.IOException;

public class TemporaryDirectory  {

    private final String folderName;

    public TemporaryDirectory(String folderName) {
        this.folderName = folderName;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public File create() {
        try {
            File folder = File.createTempFile(folderName, "");
            folder.delete();
            folder.mkdir();
            return folder;
        } catch (IOException e) {
            throw new RuntimeException("Could not create folder.", e);
        }
    }
}