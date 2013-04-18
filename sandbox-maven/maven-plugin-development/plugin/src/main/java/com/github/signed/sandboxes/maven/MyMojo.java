package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.model.FileHeader;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.resolver.ArtifactResolver;
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
 */
@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
    private final ZipDumper zipDumper = new ZipDumper();
    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Component
    private MavenProject mavenProject;
    @Component
    private ArtifactFactory artifactFactory;
    @Component
    private ArtifactResolver artifactResolver;


    public void execute() throws MojoExecutionException {


        searchForLicenseInArtifact();

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

    private void searchForLicenseInArtifact() throws MojoExecutionException {

        if (null == mavenProject) {
            getLog().error("project is null");
        } else {
            Set<Artifact> artifacts = mavenProject.getDependencyArtifacts();
            try {
                for (Artifact artifact : artifacts) {
                    String path = artifact.getFile().getPath();
                    listContentOfZip(artifact, path, new File(path));
                }
            } catch (net.lingala.zip4j.exception.ZipException e) {
                throw new MojoExecutionException("unable to open artifact as zip", e);
            }

        }
    }

    private void listContentOfZip(Artifact artifact, String path, File file) throws net.lingala.zip4j.exception.ZipException {
        getLog().info(artifact.getId());
        zipDumper.dumpZipContent(file, new LegalRelevantFiles() {
            @Override
            public void licenseFile(FileHeader license) {
                getLog().info(license.getFileName());
            }
            @Override
            public void noticeFile(FileHeader notice) {
                getLog().info(notice.getFileName());
            }
        });
    }

}
