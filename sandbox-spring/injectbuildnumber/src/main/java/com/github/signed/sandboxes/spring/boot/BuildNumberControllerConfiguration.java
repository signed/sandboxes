package com.github.signed.sandboxes.spring.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildNumberControllerConfiguration {

    @Bean
    public BuildNumberController sampleController(){
        return new BuildNumberController();
    }
}
