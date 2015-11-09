package com.github.signed.sanboxes.spring.advices;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class AdvisedControllerApplicationConfiguration {

    @Bean
    public AdvisedController advisedController(){
        return new AdvisedController();
    }
}
