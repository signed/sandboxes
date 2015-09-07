package com.github.signed.sandboxes.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/git.properties")
public class BuildNumberControllerConfiguration {

    @Autowired
    private Environment environment;

    public static class ApplicationVersion{
        public final String commitHash;

        public ApplicationVersion(String commitHash) {
            this.commitHash = commitHash;
        }
    }

    @Bean
    public BuildNumberController sampleController() {
        String commitHash = environment.getProperty("git.commit.id");
        ApplicationVersion applicationVersion = new ApplicationVersion(commitHash);
        return new BuildNumberController(applicationVersion);
    }

}
