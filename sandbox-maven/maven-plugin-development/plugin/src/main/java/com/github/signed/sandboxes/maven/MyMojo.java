package com.github.signed.sandboxes.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;


/**
 * Goal which touches a timestamp file.
 *
 * @deprecated Don't use!
 */
@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    /**
     * The Maven Project Object
     *
     * @since 1.0
     */
    @Component
    private MavenProject project;

    public void execute() throws MojoExecutionException {

        if (null == project) {
            getLog().error("project is null");
        } else {
            Set<Artifact> artifacts = project.getDependencyArtifacts();
            for (Artifact artifact : artifacts) {
                String path = artifact.getFile().getPath();
                getLog().error("-----");
                getLog().warn("found you at" + path);
                getLog().error("-----");
            }
        }

        getLog().warn("I am touching you ...");
        File f = outputDirectory;

        if (!f.exists()) {
            f.mkdirs();
        }

        File touch = new File(f, "touch.txt");

        FileWriter w = null;
        try {
            w = new FileWriter(touch);

            w.write("touch.txt");
        } catch (IOException e) {
            throw new MojoExecutionException("Error creating file " + touch, e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
