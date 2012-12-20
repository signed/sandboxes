package jersey;

import com.google.common.collect.Maps;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

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
        //someWayToConfigureJerseyOverAClass();
        serve("/*").with(GuiceContainer.class, params);
    }

    private void someWayToConfigureJerseyOverAClass() {
        params.put(ServletContainer.RESOURCE_CONFIG_CLASS, ClassPathResourceConfig.class.getName());
    }

    private void scanThisPackageAndItsSubpackagesForResources(String packageToScan) {
        params.put(PackagesResourceConfig.PROPERTY_PACKAGES, packageToScan);
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