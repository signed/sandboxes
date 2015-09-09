package com.github.signed.sandboxes.spring.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ExternalConfigurationControllerConfiguration {

    @Bean
    public ExternalConfigurationController sampleController(Environment environment){
        return new ExternalConfigurationController(environment);
    }
}
