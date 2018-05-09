package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableAutoConfiguration
public class ExternalConfigurationBootApplication {

    @Configuration
    @Profile("dev")
    public static class Dev {
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(ExternalConfigurationBootApplication.class)
                .run(args);
    }

}
