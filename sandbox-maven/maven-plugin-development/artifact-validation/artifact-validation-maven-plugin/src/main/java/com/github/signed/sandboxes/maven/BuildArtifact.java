package com.github.signed.sandboxes.maven;

import java.io.File;

public interface BuildArtifact {
    String classifier();
    File location();
}
