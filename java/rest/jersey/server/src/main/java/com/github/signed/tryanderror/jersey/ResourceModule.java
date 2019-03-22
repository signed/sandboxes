package com.github.signed.tryanderror.jersey;

import com.github.signed.tryanderror.resources.root.BelowRoot;
import com.github.signed.tryanderror.resources.root.RootResource;
import com.github.signed.tryanderror.resources.todo.TodoResource;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ResourceModule extends JerseyServletModule {
    @Override
    protected void configureServlets() {

        bind(RootResource.class);
        bind(BelowRoot.class);
        bind(TodoResource.class);
        ImmutableMap<String, String> params = ImmutableMap.of(
                JSONConfiguration.FEATURE_POJO_MAPPING, "true",
                "com.sun.jersey.spi.container.ContainerResponseFilters", "com.sun.jersey.server.linking.LinkFilter"
        );
        serve("/*").with(GuiceContainer.class, params);
    }
}