/*
 * Copyright 2008-2013 Don Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.signed.sandboxes.maven;

import com.github.signed.sandboxes.maven.artifacts.ArtifactSink;
import com.github.signed.sandboxes.maven.artifacts.ArtifactsCreatedByThisBuild;
import com.github.signed.sandboxes.maven.plugins.BuildPlugins;
import com.github.signed.sandboxes.maven.plugins.PluginCallback;
import com.github.signed.sandboxes.maven.surefire.ConfigurationSink;
import com.github.signed.sandboxes.maven.surefire.ConfigurationTemplate;
import com.github.signed.sandboxes.maven.surefire.SureFireExecution;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.util.List;

import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

/**
 * Execute surefire in verify phase and run tests against the generated artifacts
 */
@Mojo(name = "execute-surefire", requiresDependencyResolution = ResolutionScope.TEST, defaultPhase = LifecyclePhase.VERIFY)
public class ExecuteSurefireMojo extends AbstractMojo {

    /**
     * The project currently being build.
     */
    @Component
    private MavenProject mavenProject;

    /**
     * The current Maven session.
     */
    @Component
    private MavenSession mavenSession;

    /**
     * The Maven BuildPluginManager component.
     */
    @Component
    private BuildPluginManager pluginManager;

    @Parameter(defaultValue = "${project.artifact}", required = true, readonly = true)
    private Artifact artifact;

    @Parameter(defaultValue = "${project.attachedArtifacts}", required = true, readonly = true)
    private List<Artifact> attachedArtifacts;

    private final SureFireExecution sureFireExecution = new SureFireExecution();


    public void execute() throws MojoExecutionException {

        BuildPlugins buildPlugins = new BuildPlugins(mavenProject);
        buildPlugins.lookup("org.apache.maven.plugins:maven-surefire-plugin", new PluginCallback() {
            @Override
            public void plugin(Plugin plugin) {
                sureFireExecution.useSurefireVersion(plugin.getVersion());
            }

            @Override
            public void notFound(String pluginKey) {
                //do nothing, use the default version
            }
        });
        final ConfigurationTemplate configurationTemplate = new ConfigurationTemplate();
        ArtifactsCreatedByThisBuild artifacts = new ArtifactsCreatedByThisBuild(artifact, attachedArtifacts);
        artifacts.handArtifactsTo(new ArtifactSink() {
            @Override
            public void consume(Iterable<BuildArtifact> artifacts) {
                    configurationTemplate.addArgumentsFor(artifacts);
            }
        });

        configurationTemplate.attachConfigurationTo(new ConfigurationSink() {
            @Override
            public void consume(Xpp3Dom configuration) {
                sureFireExecution.useConfiguration(configuration);
            }
        });
        sureFireExecution.in(executionEnvironment(mavenProject, mavenSession, pluginManager));
        sureFireExecution.execute();
    }
}