package com.github.signed.sandboxes.maven.plugins;

import org.apache.maven.model.Plugin;
import org.apache.maven.project.MavenProject;

import java.util.List;

public class BuildPlugins {

    private final MavenProject mavenProject;

    public BuildPlugins(MavenProject mavenProject) {
        this.mavenProject = mavenProject;
    }

    public void lookup(String pluginId, PluginCallback callback) {
        List<Plugin> plugins = mavenProject.getBuildPlugins();
        for (Plugin plugin : plugins) {
            if (pluginId.equals(plugin.getKey())) {
                callback.plugin(plugin);
                return;
            }
        }
        callback.notFound(pluginId);
    }
}