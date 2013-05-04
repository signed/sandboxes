package com.github.signed.sandboxes.maven;

import com.github.signed.sandboxes.maven.surefire.Stuff;

public interface ArtifactSink {
    void consume(Iterable<Stuff> artifacts);
}
