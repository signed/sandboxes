package com.github.signed.sandboxes.spring.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

@Configuration
public class ControllerConfiguration {

    //@Bean
    public HandlerMapping handlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MAX_VALUE - 2);

        Properties urlProperties = new Properties();
        urlProperties.put("/index", "catchAllController");

        mapping.setMappings(urlProperties);
        return mapping;
    }

}
