package com.github.signed.sandboxes.maven.surefire;

import java.io.File;
import java.io.IOException;

public class ArtifactToPropertiesWriter {
    private final File rootDirectory;

    public ArtifactToPropertiesWriter(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void write() throws IOException {
        new File(this.rootDirectory, "artifacts.properties").createNewFile();
    }
}
