package com.github.signed.boot.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class AdjustLogPatternPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        String system = environment.getProperty("logging.environment");
        Map<String, Object> map = new HashMap<>();
        //map.put("logging.pattern.level", "%clr(%5p) %clr([${spring.application.name:},%X{X-B3-TraceId:-},%X{X-B3-SpanId:-},%X{X-Span-Export:-}]){yellow}");
        map.put("logging.pattern.level", "env=" + system + " lvl=%p request=%mdc{requestId:-none}");
        propertySources.addLast(new MapPropertySource(PROPERTY_SOURCE_NAME, map));
    }

    private static final String PROPERTY_SOURCE_NAME = "defaultProperties";
}
