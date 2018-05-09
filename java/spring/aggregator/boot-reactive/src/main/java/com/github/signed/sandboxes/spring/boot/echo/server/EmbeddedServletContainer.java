package com.github.signed.sandboxes.spring.boot.echo.server;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedServletContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public static final int PORT = 9000;

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(PORT);
    }
}
