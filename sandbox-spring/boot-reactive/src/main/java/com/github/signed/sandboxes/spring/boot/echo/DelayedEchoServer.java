package com.github.signed.sandboxes.spring.boot.echo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DelayedEchoServer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(EchoController.class, args);
    }
}
