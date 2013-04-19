package com.github.signed.sandboxes.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.AbstractArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransitiveArtifactResolver {

    private final ArtifactFactory artifactFactory;
    private final ArtifactRepository localRepository;
    private final ArtifactMetadataSource artifactMetadataSource;
    private final ArtifactResolver artifactResolver;

    public TransitiveArtifactResolver(ArtifactFactory artifactFactory, ArtifactRepository localRepository, ArtifactMetadataSource artifactMetadataSource, ArtifactResolver artifactResolver) {
        this.artifactFactory = artifactFactory;
        this.localRepository = localRepository;
        this.artifactMetadataSource = artifactMetadataSource;
        this.artifactResolver = artifactResolver;
    }

    @SuppressWarnings("unchecked")
    public Set<Artifact> allTransitiveDependencies(MavenProject mavenProject, ArrayList<ArtifactRepository> repoList) throws MojoExecutionException {
        Set<Artifact> directDependencies = mavenProject.getDependencyArtifacts();
        Set<Artifact> allDependencies = new HashSet<Artifact>();
        for (Artifact directDependency : directDependencies) {
            allDependencies.addAll(resolveTransitiveDependencies(directDependency, repoList));
        }
        return allDependencies;
    }

    @SuppressWarnings("unchecked")
    private Set<Artifact> resolveTransitiveDependencies(Artifact toDownload, List<ArtifactRepository> repoList) throws MojoExecutionException {
        try {
            ArtifactResolutionResult resolutionResult = this.artifactResolver.resolveTransitively(Collections.singleton(toDownload), dummyOriginatingArtifact(), repoList, localRepository, artifactMetadataSource);
            return resolutionResult.getArtifacts();
        } catch (AbstractArtifactResolutionException e) {
            throw new MojoExecutionException("Couldn't download artifact: " + e.getMessage(), e);
        }
    }

    private Artifact dummyOriginatingArtifact() {
        return artifactFactory.createBuildArtifact("org.apache.maven.plugins", "maven-downloader-plugin", "1.0", "jar");
    }
}
