package com.github.signed.sandboxes.spring.boot.caller.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CallerBootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CallerBootApplication.class, args);
    }
}
