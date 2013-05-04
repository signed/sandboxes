package com.github.signed.sandboxes.maven;

import com.github.signed.sandboxes.maven.surefire.ArtifactToPropertiesWriter;
import com.github.signed.sandboxes.maven.surefire.Stuff;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;

import java.io.File;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "passArtifactsToSurefire", defaultPhase = LifecyclePhase.VERIFY)
public class PassArtifactsToSurefireMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.artifact}", required = true, readonly = true)
    private Artifact artifact;

    @Parameter(defaultValue = "${project.attachedArtifacts}", required = true, readonly = true)
    private List<Artifact> attachedArtifacts;


    @Parameter(readonly = true, defaultValue = "${project}", required = true)
    private MavenProject mavenProject;

    public void execute() throws MojoExecutionException {
        dumpArtifactInfo(artifact);

        System.out.println("attached artifacts: " + attachedArtifacts.size());
        for (Artifact artifact : attachedArtifacts) {
            dumpArtifactInfo(artifact);
        }

        BuildPlugins buildPlugins = new BuildPlugins(mavenProject);
        buildPlugins.lookup("org.apache.maven.plugins:maven-surefire-plugin", new PluginCallback() {
            @Override
            public void plugin(Plugin plugin) {
                List<PluginExecution> executions = plugin.getExecutions();
                for (final PluginExecution execution : executions) {
                    if("verify".equalsIgnoreCase(execution.getPhase())){
                        Xpp3Dom configuration = (Xpp3Dom)execution.getConfiguration();
                        String systemPropertiesFile = configuration.getChild("systemPropertiesFile").getValue();
                        new ArtifactToPropertiesWriter(new File(systemPropertiesFile)).write(allArtifacts());
                    }
                }
            }

            @Override
            public void notFound(String pluginKey) {
                System.out.println("not found: " + pluginKey);
            }
        });
    }

    private Iterable<Stuff> allArtifacts() {
        List<Stuff> result = new ArrayList<Stuff>();
        result.add(convert(this.artifact));
        for (Artifact attachedArtifact : attachedArtifacts) {
            result.add(convert(attachedArtifact));
        }
        return result;
    }

    private Stuff convert(final Artifact artifact) {
        return new ArtifactAdapter(artifact);
    }

    private static void dumpArtifactInfo(Artifact artifact) {
        PrintStream out = System.out;
        out.println("artifact: " + artifact.getArtifactId());
        out.println("classifer: " +artifact.getClassifier());
        out.println("location:" + artifact.getFile());
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
                shout("execution id: " + execution.getId());
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