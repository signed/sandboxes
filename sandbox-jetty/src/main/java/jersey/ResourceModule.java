package jersey;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ResourceModule extends JerseyServletModule {
    @Override
    protected void configureServlets() {
        bind(RootResource.class);
        ImmutableMap<String, String> params = ImmutableMap.of(
                JSONConfiguration.FEATURE_POJO_MAPPING, "true",
                "com.sun.jersey.spi.container.ContainerResponseFilters", "com.sun.jersey.server.linking.LinkFilter"
        );
        serve("/*").with(GuiceContainer.class, params);
    }
}