package com.github.signed.sandboxes.spring.beanvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableAutoConfiguration
public class BootWithBeanValidationApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootWithBeanValidationApplication.class, args);
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        //factory.setValidationMessageSource(messageSource());
        return factory;
    }
}
