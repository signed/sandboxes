package com.github.signed.sandboxes.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import(value = {ControllerConfiguration.class})
public class BootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootApplication.class, args);
    }

}
