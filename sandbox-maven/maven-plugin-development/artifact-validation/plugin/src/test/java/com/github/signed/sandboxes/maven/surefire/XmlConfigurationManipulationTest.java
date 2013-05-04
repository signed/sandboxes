package com.github.signed.sandboxes.maven.surefire;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class XmlConfigurationManipulationTest {
    private final ConfigurationTemplate configurationTemplate = new ConfigurationTemplate();

    @Test
    public void showAndTell() throws Exception {
        List<Stuff> stuff = new ArrayList<Stuff>();
        stuff.add(DummyArtifact.defaultArtifactAt("brot"));
        stuff.add(DummyArtifact.attachedArtifactWith("juhu", "asfdasd"));

        configurationTemplate.addArgumentsFor(stuff);
        configurationTemplate.attachConfigurationTo(new ConfigurationSink() {
            @Override
            public void consume(Xpp3Dom dom) {
                System.out.println(dom);
            }
        });
    }
}