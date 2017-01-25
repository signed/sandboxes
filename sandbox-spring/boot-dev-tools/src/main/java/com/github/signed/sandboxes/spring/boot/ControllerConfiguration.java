package com.github.signed.sandboxes.spring.boot;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfiguration {

    public static final String CollaboratorBeanName = "collaborator";

    @Bean(name = CollaboratorBeanName, autowire = Autowire.BY_TYPE)
    public Collaborator collaboratorFoo() {
        return new PoliteCollaborator();
    }

    @Bean
    public SampleController sampleController(Collaborator collaborator){
        return new SampleController(collaborator);
    }
}
