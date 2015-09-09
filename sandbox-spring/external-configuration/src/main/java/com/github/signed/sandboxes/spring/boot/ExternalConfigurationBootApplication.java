package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(value = {ExternalConfigurationControllerConfiguration.class})
public class ExternalConfigurationBootApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(ExternalConfigurationBootApplication.class)
                .run(args);

    }

}
