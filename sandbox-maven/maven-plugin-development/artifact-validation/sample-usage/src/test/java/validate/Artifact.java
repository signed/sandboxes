package validate;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class Artifact {
    private final File file = new File(System.getProperties().getProperty("artifact", "not present"));

    public boolean contains(String location) {
        return ZipUtil.containsEntry(file, location);
    }
}