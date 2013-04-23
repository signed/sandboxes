package com.github.signed.sandboxes.maven;

import net.lingala.zip4j.model.FileHeader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.License;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Parameter( defaultValue = "${project.remoteArtifactRepositories}", readonly = true )
    private List remoteRepositories;

    @Component
    private ArtifactMetadataSource source;
    @Component
    private MavenProjectBuilder mavenProjectBuilder;


    public void execute() throws MojoExecutionException {
        Set<Artifact> artifacts = new TransitiveArtifactResolver(artifactFactory, localRepository, source, artifactResolver).allTransitiveDependencies(mavenProject, repositoriesToSearchForArtifacts());

        for (Artifact artifact : artifacts) {

            if("jar".equals(artifact.getType())){
                try {
                    findLicenseInformation(artifact, artifact.getFile());
                } catch (net.lingala.zip4j.exception.ZipException e) {
                    getLog().error(String.format("unable to check artifact at '%s'.", artifact.getFile().getAbsolutePath()));
                } catch (IOException e) {
                    getLog().error("could not write legal files");
                }
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

    private MavenProject buildProjectFrom(Artifact artifact){
        try {
            MavenProject depMavenProject = mavenProjectBuilder.buildFromRepository( artifact, remoteRepositories, localRepository, true );
            depMavenProject.getArtifact().setScope( artifact.getScope() );
            return depMavenProject;
        } catch (ProjectBuildingException e) {
            throw new RuntimeException(e);
        }
    }

    private void findLicenseInformation(Artifact artifact, File file) throws net.lingala.zip4j.exception.ZipException, IOException {
        getLog().info(artifact.getId());

        MavenProject mavenProjectForArtifact = buildProjectFrom(artifact);
        List<License> licenses = mavenProjectForArtifact.getLicenses();

        boolean isCDDL = false;
        for (License license : licenses) {
            isCDDL = isCDDL || StringUtils.containsIgnoreCase(license.getName(),"cddl");
            getLog().error(license.getName());
        }
        getLog().error("was cddl: "+ isCDDL);

        String sub = artifact.getGroupId().replaceAll("\\.", "/") + "/" + artifact.getArtifactId() + "/" + artifact.getVersion();

        final File artifactDirectory = new File(outputDirectory, sub);
        FileUtils.forceMkdir(artifactDirectory);
        final SingleFileUnzip unzip = new SingleFileUnzip(artifact.getFile());

        zipDumper.dumpZipContent(file, new LegalRelevantFiles() {

            @Override
            public void licenseFile(FileHeader license) {
                getLog().info(license.getFileName());
                unzip.unzip(license, artifactDirectory);
            }

            @Override
            public void noticeFile(FileHeader notice) {
                getLog().info(notice.getFileName());
                unzip.unzip(notice, artifactDirectory);
            }
        });
    }
}