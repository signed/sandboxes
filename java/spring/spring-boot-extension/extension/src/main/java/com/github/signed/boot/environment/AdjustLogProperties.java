package com.github.signed.boot.environment;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("logging")
public class AdjustLogProperties {

    private String environment;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
