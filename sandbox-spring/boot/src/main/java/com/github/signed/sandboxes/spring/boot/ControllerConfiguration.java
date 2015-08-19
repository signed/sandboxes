package com.github.signed.sandboxes.spring.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfiguration {

    @Bean
    public Collaborator politeCollaborator() {
        return new PoliteCollaborator();
    }

    @Bean
    public SampleController sampleController(Collaborator collaborator){
        return new SampleController(collaborator);
    }
}
