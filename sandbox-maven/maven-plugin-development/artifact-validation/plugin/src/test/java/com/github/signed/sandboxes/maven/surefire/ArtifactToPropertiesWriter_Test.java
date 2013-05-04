package com.github.signed.sandboxes.maven.surefire;

import com.github.signed.sandboxes.maven.BuildArtifact;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArtifactToPropertiesWriter_Test {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();
    private final List<BuildArtifact> artifacts = new ArrayList<BuildArtifact>();

    private ArtifactToPropertiesWriter writer;
    private File outFile;

    @Before
    public void setUp() throws Exception {
        File rootDirectory = folder.getRoot();
        outFile = new File(rootDirectory, "artifacts.properties");
        writer = new ArtifactToPropertiesWriter(outFile);
    }

    @Test
    public void createThePropertiesFile() throws Exception {
        writeArtifacts();
        assertThat(outFile.exists(), is(true));
    }

    @Test
    public void writeTheArtifactToFile() throws Exception {
        artifacts.add(DummyBuildArtifact.defaultArtifactAt("secret-location"));
        writeArtifacts();

        assertThat(readProperty("maven.artifact"), CoreMatchers.endsWith("secret-location"));
    }

    @Test
    public void appendTheClassifierToThePropertyKey() throws Exception {
        artifacts.add(DummyBuildArtifact.attachedArtifactWith("classic", "some-place"));
        writeArtifacts();

        assertThat(readProperty("maven.artifact.classic"), endsWith("some-place"));
    }

    private void writeArtifacts() throws IOException {
        writer.write(artifacts);
    }

    @Test
    public void appendToAlreadyExistingProperties() throws Exception {
        Properties properties = new Properties();
        properties.put("previously", "written");
        properties.store(new FileWriter(outFile), null);

        writeArtifacts();

        assertThat(readProperty("previously"), is("written"));
    }



    private String readProperty(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(outFile));
        return properties.getProperty(key);
    }
}
