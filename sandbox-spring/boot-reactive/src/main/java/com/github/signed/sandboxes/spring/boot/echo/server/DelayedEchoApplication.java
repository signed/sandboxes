package com.github.signed.sandboxes.spring.boot.echo.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAutoConfiguration
public class DelayedEchoApplication {
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .showBanner(false)
                .sources(DelayedEchoApplication.class)
                .run(args);
    }
}
