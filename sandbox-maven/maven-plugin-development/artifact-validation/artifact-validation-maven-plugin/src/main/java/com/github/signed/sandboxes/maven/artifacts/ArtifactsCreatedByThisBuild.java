package com.github.signed.sandboxes.maven.artifacts;

import com.github.signed.sandboxes.maven.BuildArtifact;
import org.apache.maven.artifact.Artifact;

import java.util.ArrayList;
import java.util.List;

public class ArtifactsCreatedByThisBuild {
    private final Artifact artifact;
    private final List<Artifact> attachedArtifacts;

    public ArtifactsCreatedByThisBuild(Artifact artifact, List<Artifact> attachedArtifacts) {
        this.artifact = artifact;
        this.attachedArtifacts = attachedArtifacts;
    }

    public void handArtifactsTo(ArtifactSink sink) {
        sink.consume(allArtifacts());
    }

    private Iterable<BuildArtifact> allArtifacts() {
        List<BuildArtifact> result = new ArrayList<BuildArtifact>();
        result.add(convert(this.artifact));
        for (Artifact attachedArtifact : attachedArtifacts) {
            result.add(convert(attachedArtifact));
        }
        return result;
    }

    private BuildArtifact convert(Artifact artifact) {
        return new ArtifactAdapter(artifact);
    }
}