package com.github.signed.sandboxes.maven.surefire;

import com.github.signed.sandboxes.maven.BuildArtifact;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;

import java.io.StringReader;

public class ConfigurationTemplate {
    private Iterable<BuildArtifact> artifacts;

    private Xpp3Dom configuration() {
        String configuration = "<configuration>\n" +
                "         <failIfNoTests>true</failIfNoTests>\n" +
                "         <includes>\n" +
                "             <include>**/*Validate.java</include>\n" +
                "         </includes>\n" +
                "     </configuration>\n";

        return parseSilent(configuration);
    }


    public void addArgumentsFor(Iterable<BuildArtifact> artifacts) {
        this.artifacts = artifacts;
    }

    private Xpp3Dom all(Iterable<BuildArtifact> artifacts) {
        Xpp3Dom systemProperties = systemProperties();

        String rawProperty = "<property>\n" +
                "                 <name>maven.artifact%s</name>\n" +
                "                 <value>%s</value>\n" +
                "             </property>\n";


        for (BuildArtifact artifact : artifacts) {
            String classifier = (null == artifact.classifier()) ? "" : "." + artifact.classifier();
            String absolutePath = artifact.location().getAbsolutePath();
            String propertyXml = String.format(rawProperty, classifier, absolutePath);
            systemProperties.addChild(parseSilent(propertyXml));
        }
        return systemProperties;
    }

    private Xpp3Dom systemProperties(){
        String systemPropertiesXml = "<systemProperties>\n" +
                "                     </systemProperties>\n";
        return parseSilent(systemPropertiesXml);
    }

    private Xpp3Dom parseSilent(String systemPropertiesXml) {
        try {
            return Xpp3DomBuilder.build(new StringReader(systemPropertiesXml));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void attachConfigurationTo(ConfigurationSink sink) {
        Xpp3Dom conf = configuration();
        conf.addChild(   all(artifacts));
        sink.consume(conf);
    }

}
