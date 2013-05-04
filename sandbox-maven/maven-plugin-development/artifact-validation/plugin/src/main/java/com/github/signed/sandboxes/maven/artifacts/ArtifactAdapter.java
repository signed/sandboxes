package com.github.signed.sandboxes.maven.artifacts;

import com.github.signed.sandboxes.maven.BuildArtifact;
import org.apache.maven.artifact.Artifact;

import java.io.File;

public class ArtifactAdapter implements BuildArtifact {
    private final Artifact artifact;

    public ArtifactAdapter(Artifact artifact) {
        this.artifact = artifact;
    }

    @Override
    public String classifier() {
        return artifact.getClassifier();
    }

    @Override
    public File location() {
        return artifact.getFile();
    }
}
