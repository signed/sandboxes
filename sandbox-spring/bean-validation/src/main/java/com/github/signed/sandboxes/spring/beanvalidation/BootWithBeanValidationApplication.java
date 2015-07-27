package com.github.signed.sandboxes.spring.beanvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootWithBeanValidationApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ValidatingController.class, args);
    }
}
