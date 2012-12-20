package jersey;

import com.sun.jersey.api.core.ResourceConfig;

import java.util.Map;

public class ClassPathResourceConfig extends ResourceConfig {
    @Override
    public Map<String, Boolean> getFeatures() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getFeature(String featureName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getProperties() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getProperty(String propertyName) {
        throw new UnsupportedOperationException();
    }
}
