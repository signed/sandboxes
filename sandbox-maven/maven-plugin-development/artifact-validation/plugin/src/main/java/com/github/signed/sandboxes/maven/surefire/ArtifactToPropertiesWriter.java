package com.github.signed.sandboxes.maven.surefire;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ArtifactToPropertiesWriter {
    private final File rootDirectory;

    public ArtifactToPropertiesWriter(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void write(Iterable<Stuff> artifacts) throws IOException {
        Properties properties = new Properties();
        populateProperties(artifacts, properties);
        writeProperties(properties);
    }

    private void populateProperties(Iterable<Stuff> artifacts, Properties properties) {
        for (Stuff artifact : artifacts) {
            String key = "maven.artifact" + (null == artifact.classifier()? "":"."+artifact.classifier());
            properties.put(key, artifact.location().getAbsolutePath());
        }
    }

    private void writeProperties(Properties properties) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputFile());
            properties.store(writer, "Create by the artifact-validation-maven-plugin");
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    private File outputFile() {
        return new File(this.rootDirectory, "artifacts.properties");
    }
}
