package com.github.signed.sandboxes.maven;

import com.github.signed.sandboxes.maven.surefire.Stuff;
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

    private Iterable<Stuff> allArtifacts() {
        List<Stuff> result = new ArrayList<Stuff>();
        result.add(convert(this.artifact));
        for (Artifact attachedArtifact : attachedArtifacts) {
            result.add(convert(attachedArtifact));
        }
        return result;
    }

    private Stuff convert(Artifact artifact) {
        return new ArtifactAdapter(artifact);
    }

}
