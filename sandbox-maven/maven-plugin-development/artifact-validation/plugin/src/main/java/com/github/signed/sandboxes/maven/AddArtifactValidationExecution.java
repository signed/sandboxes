package com.github.signed.sandboxes.maven;

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;

import java.util.List;

@Mojo(name = "configure", defaultPhase = LifecyclePhase.VALIDATE)
public class AddArtifactValidationExecution extends AbstractMojo {

    @Component
    private MavenProject mavenProject;
    @Component
    private ArtifactFactory artifactFactory;
    @Component
    private ArtifactResolver artifactResolver;


    @Component
    private ArtifactMetadataSource source;
    @Component
    private MavenProjectBuilder mavenProjectBuilder;


    public void execute() throws MojoExecutionException {
        List<Plugin> buildPlugins = mavenProject.getBuildPlugins();

        shout("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        for (Plugin buildPlugin : buildPlugins) {
            shout(buildPlugin.toString());
        }
    }

    private void shout(String message) {
        getLog().error(message);
    }
}