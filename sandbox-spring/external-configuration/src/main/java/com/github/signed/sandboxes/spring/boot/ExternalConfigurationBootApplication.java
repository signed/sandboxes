package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
public class ExternalConfigurationBootApplication {

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:dev.application.properties")
    public static class Dev {
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(ExternalConfigurationBootApplication.class)
                .run(args);

    }

}
