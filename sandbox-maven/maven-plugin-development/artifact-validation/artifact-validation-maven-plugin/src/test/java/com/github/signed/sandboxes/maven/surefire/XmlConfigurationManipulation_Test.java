package com.github.signed.sandboxes.maven.surefire;

import com.github.signed.sandboxes.maven.BuildArtifact;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class XmlConfigurationManipulation_Test {
    private final ConfigurationTemplate configurationTemplate = new ConfigurationTemplate();

    @Test
    public void addEachArtifactAsASystemProperty() throws Exception {
        List<BuildArtifact> buildArtifact = new ArrayList<BuildArtifact>();
        buildArtifact.add(DummyBuildArtifact.defaultArtifactAt("brot"));
        buildArtifact.add(DummyBuildArtifact.attachedArtifactWith("juhu", "asfdasd"));
        configurationTemplate.addArgumentsFor(buildArtifact);
        final Xpp3Dom[] dome = new Xpp3Dom[1];
        configurationTemplate.attachConfigurationTo(new ConfigurationSink() {
            @Override
            public void consume(Xpp3Dom dom) {
                dome[0] = dom;
            }
        });
        assertThat(dome[0].toString(), CoreMatchers.allOf(containsString("brot"), containsString("<name>maven.artifact.juhu</name>")));
    }
}