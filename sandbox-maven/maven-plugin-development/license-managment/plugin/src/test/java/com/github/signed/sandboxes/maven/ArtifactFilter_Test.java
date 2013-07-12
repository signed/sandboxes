package com.github.signed.sandboxes.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.shared.artifact.filter.PatternExcludesArtifactFilter;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class ArtifactFilter_Test {

    @Test
    public void dontIncludeStuffThatIsExcluded() throws Exception {
        VersionRange range = VersionRange.createFromVersion("3.0.2");
        Artifact artifact = new DefaultArtifact("com.github.signed", "dummyd", range, "test", "jar", "", null, false);

        List<String> patterns = new ArrayList<String>();
        patterns.add("*:dummy*:*");

        PatternExcludesArtifactFilter patternExcludesArtifactFilter = new PatternExcludesArtifactFilter(patterns, false);
        MatcherAssert.assertThat(patternExcludesArtifactFilter.include(artifact), is(false));
    }
}