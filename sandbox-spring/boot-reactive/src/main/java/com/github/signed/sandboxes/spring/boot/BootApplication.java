package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
