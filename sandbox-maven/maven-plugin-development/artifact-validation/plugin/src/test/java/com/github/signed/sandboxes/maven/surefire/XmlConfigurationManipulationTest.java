package com.github.signed.sandboxes.maven.surefire;

import com.github.signed.sandboxes.maven.BuildArtifact;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class XmlConfigurationManipulationTest {
    private final ConfigurationTemplate configurationTemplate = new ConfigurationTemplate();

    @Test
    public void showAndTell() throws Exception {
        List<BuildArtifact> buildArtifact = new ArrayList<BuildArtifact>();
        buildArtifact.add(DummyBuildArtifact.defaultArtifactAt("brot"));
        buildArtifact.add(DummyBuildArtifact.attachedArtifactWith("juhu", "asfdasd"));

        configurationTemplate.addArgumentsFor(buildArtifact);
        configurationTemplate.attachConfigurationTo(new ConfigurationSink() {
            @Override
            public void consume(Xpp3Dom dom) {
                System.out.println(dom);
            }
        });
    }
}