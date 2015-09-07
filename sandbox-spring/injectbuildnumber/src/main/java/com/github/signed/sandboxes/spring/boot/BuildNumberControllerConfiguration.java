package com.github.signed.sandboxes.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = {"classpath:/git.properties", "classpath:/version.properties"})
public class BuildNumberControllerConfiguration {

    @Autowired
    private Environment environment;

    public static class ApplicationVersion {
        public final String commitHash;
        public final String version;
        public final String timestamp;
        public final String buildNumber;

        public ApplicationVersion(String commitHash, String version, String timestamp, String buildNumber) {
            this.commitHash = commitHash;
            this.version = version;
            this.timestamp = timestamp;
            this.buildNumber = buildNumber;
        }
    }

    @Bean
    public BuildNumberController sampleController() {
        String commitHash = environment.getProperty("git.commit.id");
        String version = environment.getProperty("application.build.version");
        String timestamp = environment.getProperty("application.build.timestamp");
        String buildNumber = environment.getProperty("application.build.number");
        ApplicationVersion applicationVersion = new ApplicationVersion(commitHash, version, timestamp, buildNumber);
        return new BuildNumberController(applicationVersion);
    }

}
