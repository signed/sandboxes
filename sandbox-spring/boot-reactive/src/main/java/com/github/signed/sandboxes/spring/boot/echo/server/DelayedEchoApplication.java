package com.github.signed.sandboxes.spring.boot.echo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DelayedEchoApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DelayedEchoApplication.class, args);
    }
}
