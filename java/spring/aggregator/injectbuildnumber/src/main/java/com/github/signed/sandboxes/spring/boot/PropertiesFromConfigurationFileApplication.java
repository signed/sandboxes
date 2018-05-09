package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(BuildNumberControllerConfiguration.class)
public class PropertiesFromConfigurationFileApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(PropertiesFromConfigurationFileApplication.class)
                .run(args);

    }

}
