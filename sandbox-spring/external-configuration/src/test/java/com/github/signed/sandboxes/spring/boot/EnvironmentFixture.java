package com.github.signed.sandboxes.spring.boot;

import org.springframework.core.env.Environment;

public interface EnvironmentFixture {

    default String property(String propertyKey) {
        return environment().getProperty(propertyKey);
    }

    Environment environment();
}
