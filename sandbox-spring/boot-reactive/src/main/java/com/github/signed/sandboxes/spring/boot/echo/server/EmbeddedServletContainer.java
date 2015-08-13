package com.github.signed.sandboxes.spring.boot.echo.server;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedServletContainer implements EmbeddedServletContainerCustomizer {

    public static final int PORT = 9000;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(PORT);
    }
}
