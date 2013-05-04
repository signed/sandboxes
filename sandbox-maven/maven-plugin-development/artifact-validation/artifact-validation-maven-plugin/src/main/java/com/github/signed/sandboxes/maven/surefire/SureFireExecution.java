package com.github.signed.sandboxes.maven.surefire;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

public class SureFireExecution {
    private String version = "2.14.1";
    private Xpp3Dom configuration;
    private MojoExecutor.ExecutionEnvironment executionEnvironment;

    public void useConfiguration(Xpp3Dom configuration) {
        this.configuration = configuration;
    }

    public void useSurefireVersion(String version) {
        this.version = version;
    }

    public void in(MojoExecutor.ExecutionEnvironment executionEnvironment) {
        this.executionEnvironment = executionEnvironment;
    }

    public void execute() {
        try {
            executeMojo(
                    plugin(
                            groupId("org.apache.maven.plugins"),
                            artifactId("maven-surefire-plugin"),
                            version(version)
                    ),
                    goal("test"),
                    this.configuration,
                    this.executionEnvironment
            );
        } catch (MojoExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}
