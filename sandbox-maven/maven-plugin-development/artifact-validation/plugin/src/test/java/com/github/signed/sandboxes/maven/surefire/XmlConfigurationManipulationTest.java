package com.github.signed.sandboxes.maven.surefire;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class XmlConfigurationManipulationTest {

    @Test
    public void testName() throws Exception {
        ConfigurationTemplate configurationTemplate = new ConfigurationTemplate();

        Stuff stuffWithClassifier = DummyArtifact.attachedArtifactWith("juhu", "asfdasd");

        Stuff withoutClassifier = DummyArtifact.defaultArtifactAt("brot");

        List<Stuff> stuff = new ArrayList<Stuff>();
        stuff.add(withoutClassifier);
        stuff.add(stuffWithClassifier);

        configurationTemplate.addArgumentsFor(stuff);
        configurationTemplate.attachConfigurationTo(new ConfigurationSink() {
            @Override
            public void consume(Xpp3Dom dom) {
                System.out.println(dom);
            }
        });
    }

}
