package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.model.FileHeader;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.resolver.AbstractArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
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
            Set<Artifact> artifacts = getDependenciesToScan();
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

    private Set<Artifact> getDependenciesToScan() throws MojoExecutionException {
        Set<Artifact> directDependencies = mavenProject.getDependencyArtifacts();
        Set<Artifact> allDependencies = new HashSet<Artifact>();
        for (Artifact directDependency : directDependencies) {
            allDependencies.addAll(resolveTransitiveDependencies(directDependency));
        }
        return allDependencies;
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


    private Set<Artifact> resolveTransitiveDependencies(Artifact toDownload) throws MojoExecutionException {
        ArtifactRepositoryPolicy always =
                new ArtifactRepositoryPolicy(true, ArtifactRepositoryPolicy.UPDATE_POLICY_ALWAYS,
                        ArtifactRepositoryPolicy.CHECKSUM_POLICY_WARN);

        List<ArtifactRepository> repoList = new ArrayList<ArtifactRepository>();

        if (pomRemoteRepositories != null) {
            repoList.addAll(pomRemoteRepositories);
        }


        try {
            ArtifactResolutionResult resolutionResult = artifactResolver.resolveTransitively(Collections.singleton(toDownload), dummyOriginatingArtifact(), repoList, localRepository, source);
            return resolutionResult.getArtifacts();
        } catch (AbstractArtifactResolutionException e) {
            throw new MojoExecutionException("Couldn't download artifact: " + e.getMessage(), e);
        }
    }

    private Artifact dummyOriginatingArtifact() {
        return artifactFactory.createBuildArtifact("org.apache.maven.plugins", "maven-downloader-plugin", "1.0", "jar");
    }
}
