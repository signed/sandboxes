package com.github.signed.sandboxes.maven.surefire;

import java.io.File;

public class DummyArtifact implements Stuff {

    public static DummyArtifact attachedArtifactWith(String classifier, String location) {
        return new DummyArtifact(classifier, location);
    }

    public static DummyArtifact defaultArtifactAt(String location) {
        return new DummyArtifact(null, location);
    }

    private final String location;
    private final String classifier;

    DummyArtifact(String classifier, String location) {
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
