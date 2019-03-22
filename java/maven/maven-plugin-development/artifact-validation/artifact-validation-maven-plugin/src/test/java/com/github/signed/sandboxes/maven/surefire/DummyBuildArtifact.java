package com.github.signed.sandboxes.maven.surefire;

import com.github.signed.sandboxes.maven.BuildArtifact;

import java.io.File;

public class DummyBuildArtifact implements BuildArtifact {

    public static DummyBuildArtifact attachedArtifactWith(String classifier, String location) {
        return new DummyBuildArtifact(classifier, location);
    }

    public static DummyBuildArtifact defaultArtifactAt(String location) {
        return new DummyBuildArtifact(null, location);
    }

    private final String location;
    private final String classifier;

    DummyBuildArtifact(String classifier, String location) {
        this.location = location;
        this.classifier = classifier;
    }

    @Override
    public String classifier() {
        return classifier;
    }

    @Override
    public File location() {
        return new File(location);
    }
}
