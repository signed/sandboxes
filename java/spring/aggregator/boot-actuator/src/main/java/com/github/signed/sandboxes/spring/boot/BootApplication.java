package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class BootApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(BootApplication.class)
                .run(args);

    }

}
