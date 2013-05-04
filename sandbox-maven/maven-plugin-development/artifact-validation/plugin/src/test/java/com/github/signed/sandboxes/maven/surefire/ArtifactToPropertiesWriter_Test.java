package com.github.signed.sandboxes.maven.surefire;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArtifactToPropertiesWriter_Test {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    private ArtifactToPropertiesWriter writer;
    private File rootDirectory;

    @Before
    public void setUp() throws Exception {
        rootDirectory = folder.getRoot();
        writer = new ArtifactToPropertiesWriter(rootDirectory);
    }

    @Test
    public void writePropertiesFile() throws Exception {
        writer.write();
        assertThat(new File(rootDirectory, "artifacts.properties").exists(), is(true));
    }

    @Test
    public void writeTheArtifactsToTheFile() throws Exception {
    }
}
