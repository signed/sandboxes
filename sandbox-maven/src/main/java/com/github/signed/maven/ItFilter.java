package com.github.signed.maven;

import org.apache.maven.shared.filtering.DefaultMavenResourcesFiltering;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.filtering.MavenResourcesExecution;

/**
 * @plexus.component role="org.apache.maven.shared.filtering.MavenResourcesFiltering"
 *                   role-hint="itFilter"
 */
public class ItFilter extends DefaultMavenResourcesFiltering{


    @Override
    public void filterResources(MavenResourcesExecution mavenResourcesExecution) throws MavenFilteringException {
        super.filterResources(mavenResourcesExecution);
        System.out.println();
        System.out.println("move along nothing to see");
        System.out.println();
    }
}
