package com.github.signed.sandboxes.spring.boot.echo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DelayedEchoApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(EchoController.class, args);
    }
}