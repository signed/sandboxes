package com.github.signed.sandboxes.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

@Mojo(name = "addTestSourceRoot", defaultPhase = LifecyclePhase.INITIALIZE)
public class AddTestSourceRootMojo extends AbstractMojo {

    @Component
    private MavenProject mavenProject;

    public void execute() throws MojoExecutionException {
        mavenProject.addTestCompileSourceRoot("src/validate/java");
    }
}