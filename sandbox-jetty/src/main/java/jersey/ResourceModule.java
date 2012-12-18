package jersey;

import com.google.common.collect.Maps;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.Map;

public class ResourceModule extends JerseyServletModule {
    private Map<String, String> params = Maps.newHashMap();

    @Override
    protected void configureServlets() {
        configureStuff();
        bindResources();
    }

    private void configureStuff() {
        enableJsonResponses();
        iThinkItEnablesReplaceUrlWithLinksWhenRendernHtml();
        scanThisPackageAndItsSubpackagesForResources("jersey");
        serve("/*").with(GuiceContainer.class, params);
    }

    private void scanThisPackageAndItsSubpackagesForResources(String jersey) {
        params.put(PackagesResourceConfig.PROPERTY_PACKAGES, "jersey");
    }

    private void iThinkItEnablesReplaceUrlWithLinksWhenRendernHtml() {
        params.put("com.sun.jersey.spi.container.ContainerResponseFilters", "com.sun.jersey.server.linking.LinkFilter");
    }

    private void enableJsonResponses() {
        params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
    }

    private void bindResources() {
        bind(RootResource.class);
    }
}