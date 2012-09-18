package com.github.signed.maven;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Resource;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.filtering.MavenResourcesExecution;
import org.apache.maven.shared.filtering.MavenResourcesFiltering;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @plexus.component role="org.apache.maven.shared.filtering.MavenResourcesFiltering"
 *                   role-hint="itFilter"
 */
public class ItFilter implements MavenResourcesFiltering {

    @Override
    public void filterResources(List<Resource> resources, File outputDirectory, MavenProject mavenProject, String encoding, List<String> fileFilters, List<String> nonFilteredFileExtensions, MavenSession mavenSession) throws MavenFilteringException {
        throw new RuntimeException("not called");
    }

    @Override
    public void filterResources(List<Resource> resources, File outputDirectory, String encoding, List<FileUtils.FilterWrapper> filterWrappers, File resourcesBaseDirectory, List<String> nonFilteredFileExtensions) throws MavenFilteringException {
        throw new RuntimeException("not called");
    }

    @Override
    public List getDefaultNonFilteredFileExtensions() {
        return Collections.emptyList();
    }

    @Override
    public boolean filteredFileExtension(String fileName, List<String> userNonFilteredFileExtensions) {
        return fileName.endsWith(".me");
    }

    @Override
    public void filterResources(MavenResourcesExecution mavenResourcesExecution) throws MavenFilteringException {
        List<Resource> resources = mavenResourcesExecution.getResources();
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        System.out.println();
        System.out.println("maven Resource Execution"+mavenResourcesExecution);
        System.out.println("move along nothing to see");
        System.out.println();
    }
}
