package com.github.signed.sanboxes.spring.advices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class AdvisedControllerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdvisedController.class, args);
    }
}
