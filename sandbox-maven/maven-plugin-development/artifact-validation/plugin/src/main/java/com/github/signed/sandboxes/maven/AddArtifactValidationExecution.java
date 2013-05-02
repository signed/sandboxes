package com.github.signed.sandboxes.maven;

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;

import java.io.StringReader;
import java.util.List;

@Mojo(name = "configure", defaultPhase = LifecyclePhase.INITIALIZE)
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

    private BuildPlugins buildPlugins;

    public void execute() throws MojoExecutionException {
        mavenProject.addTestCompileSourceRoot("src/validate/java");
    }

    public void execute2() throws MojoExecutionException {
        BuildPlugins buildPlugins = new BuildPlugins(mavenProject);
        buildPlugins.lookup("org.apache.maven.plugins:maven-surefire-plugin", new PluginCallback() {
            @Override
            public void plugin(Plugin plugin) {
                addExecution(plugin);
                addExecution(plugin);
            }

            @Override
            public void notFound(String pluginKey) {
                throw new RuntimeException("there should be one....");
            }
        });

        List<Plugin> raw = mavenProject.getBuildPlugins();
        shout("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        for (Plugin plugin : raw) {
            shout("key: " + plugin.getKey());


            shout(plugin.toString());
            List<PluginExecution> executions = plugin.getExecutions();
            for (PluginExecution execution : executions) {
                shout("execution id: "+ execution.getId());
                Xpp3Dom configuration = (Xpp3Dom) execution.getConfiguration();
                shout("configuration: " + ((null == configuration) ? null : configuration));
            }
        }

        List<Plugin> plugins = mavenProject.getModel().getBuild().getPlugins();
        plugins.clear();

    }

    private void addExecution(Plugin plugin) {
        PluginExecution pluginExecution = new PluginExecution();
        pluginExecution.setId("validate-artifacts");
        pluginExecution.setGoals(Collections.singletonList("test"));
        pluginExecution.setPhase("verify");
        pluginExecution.setConfiguration(createConfiguration());

        plugin.addExecution(pluginExecution);
    }

    private Xpp3Dom createConfiguration() {
        try {
            String configuration = "    <configuration>\n" +
                    "                    <failIfNoTests>true</failIfNoTests>\n" +
                    "                    <includes>\n" +
                    "                        <include>**/*Validate.java</include>\n" +
                    "                    </includes>\n" +
                    "                    <systemProperties>\n" +
                    "                        <property>\n" +
                    "                            <name>artifact</name>\n" +
                    "                            <value>${project.build.directory}/${project.build.finalName}.${project.packaging}</value>\n" +
                    "                        </property>\n" +
                    "                    </systemProperties>\n" +
                    "                </configuration>";

            return Xpp3DomBuilder.build(new StringReader(configuration));
        } catch (Exception e) {
            throw new RuntimeException("failed", e);
        }
    }

    private void shout(String message) {
        getLog().error(message);
    }
}