package com.github.signed.sandboxes.maven.artifacts;

import com.github.signed.sandboxes.maven.BuildArtifact;

public interface ArtifactSink {
    void consume(Iterable<BuildArtifact> artifacts);
}
