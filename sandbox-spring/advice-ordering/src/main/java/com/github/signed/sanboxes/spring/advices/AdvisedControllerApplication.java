package com.github.signed.sanboxes.spring.advices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdvisedControllerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdvisedControllerApplicationConfiguration.class, args);
    }
}
