package com.github.signed.sandboxes.spring.boot.caller.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAutoConfiguration
public class CallerBootApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .showBanner(false)
                .sources(CallerBootApplication.class)
                .run(args);
    }
}
