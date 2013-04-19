package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.model.FileHeader;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    @Parameter(defaultValue = "${project.build.directory}/legal", property = "outputDir", required = true)
    private File outputDirectory;

    @Component
    private MavenProject mavenProject;
    @Component
    private ArtifactFactory artifactFactory;
    @Component
    private ArtifactResolver artifactResolver;

    @Parameter(defaultValue = "${project.remoteArtifactRepositories}", readonly = true, required = true)
    private List<ArtifactRepository> pomRemoteRepositories;

    @Parameter(defaultValue = "${localRepository}", readonly = true)
    private ArtifactRepository localRepository;

    @Component
    private ArtifactMetadataSource source;

    public void execute() throws MojoExecutionException {

        Set<Artifact> artifacts = new TransitiveArtifactResolver(artifactFactory, localRepository, source, artifactResolver).allTransitiveDependencies(mavenProject, repositoriesToSearchForArtifacts());

        for (Artifact artifact : artifacts) {
            try {
                listContentOfZip(artifact, artifact.getFile());
            } catch (net.lingala.zip4j.exception.ZipException e) {
                getLog().error(String.format("unable to check artifact at '%s'.", artifact.getFile().getAbsolutePath()));
            }
        }

    }

    private ArrayList<ArtifactRepository> repositoriesToSearchForArtifacts() {
        ArrayList<ArtifactRepository> repoList = new ArrayList<ArtifactRepository>();
        if (pomRemoteRepositories != null) {
            repoList.addAll(pomRemoteRepositories);
        }
        return repoList;
    }

    private void listContentOfZip(Artifact artifact, File file) throws net.lingala.zip4j.exception.ZipException {
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