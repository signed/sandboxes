package com.github.signed.sandboxes.maven.surefire;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
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
    private final List<Stuff> artifacts = new ArrayList<Stuff>();

    private ArtifactToPropertiesWriter writer;
    private File rootDirectory;

    @Before
    public void setUp() throws Exception {
        rootDirectory = folder.getRoot();
        writer = new ArtifactToPropertiesWriter(rootDirectory);
    }

    @Test
    public void writePropertiesFile() throws Exception {
        writer.write(artifacts);
        assertThat(new File(rootDirectory, "artifacts.properties").exists(), is(true));
    }

    @Test
    public void writeTheArtifactToFile() throws Exception {
        artifacts.add(DummyArtifact.defaultArtifactAt("secret-location"));
        writer.write(artifacts);

        assertThat(readProperty("maven.artifact"), CoreMatchers.endsWith("secret-location"));
    }

    @Test
    public void appendTheClassifierToThePropertyKey() throws Exception {
        artifacts.add(DummyArtifact.attachedArtifactWith("classic", "some-place"));
        writer.write(artifacts);

        assertThat(readProperty("maven.artifact.classic"), endsWith("some-place"));
    }

    private String readProperty(String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(rootDirectory, "artifacts.properties")));
        return properties.getProperty(key);
    }
}
