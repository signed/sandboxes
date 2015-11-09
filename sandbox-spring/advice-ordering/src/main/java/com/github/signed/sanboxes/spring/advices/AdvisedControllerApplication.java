package com.github.signed.sanboxes.spring.advices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.signed.sanboxes.spring.advices.configuration.AdvisedControllerApplicationConfiguration;

@SpringBootApplication
public class AdvisedControllerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdvisedControllerApplicationConfiguration.class, args);
    }
}
