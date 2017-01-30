package com.github.signed.boot.logfilter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogFilterAutoConfiguration {
    @Bean
    @Qualifier("jump")
    public String blub(){
        return "Jump";
    }
}
