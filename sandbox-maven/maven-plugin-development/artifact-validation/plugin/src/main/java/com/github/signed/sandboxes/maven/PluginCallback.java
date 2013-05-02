package com.github.signed.sandboxes.maven;

import org.apache.maven.model.Plugin;

public interface PluginCallback {
    void plugin(Plugin plugin);

    void notFound(String pluginKey);
}
