package com.github.signed.sandboxes.spring.boot;

import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import retrofit.RestAdapter;

import java.util.Optional;

import static org.springframework.boot.Banner.Mode.OFF;

public class SpringApplicationRule extends ExternalResource {

    private final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder()
            .bannerMode(OFF)
            .sources(BootApplication.class, ChooseAnAvailablePort.class);

    private final RuleConfiguration ruleConfiguration = new RuleConfiguration();
    private Optional<ConfigurableApplicationContext> context = Optional.empty();

    public <T> T client(Class<T> type) {
        ensureServerIsStarted();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUri())
                .build();
        return restAdapter.create(type);
    }

    public String baseUri() {
        return "http://localhost:" + ruleConfiguration.port();
    }

    @Override
    protected void after() {
        if (context.isPresent()) {
            SpringApplication.exit(context.get());
        }
    }

    private void ensureServerIsStarted() {
        if (context.isPresent()) {
            return;
        }
        SpringApplication springApplication = springApplicationBuilder.build();
        ConfigurableApplicationContext configurableContext = springApplication.run();

        ConfigurableListableBeanFactory beanFactory = configurableContext.getBeanFactory();
        beanFactory.initializeBean(ruleConfiguration, "ruleConfiguration");
        beanFactory.autowireBean(ruleConfiguration);
        context = Optional.of(configurableContext);
    }

    @Component
    public static class RuleConfiguration {

        public EmbeddedWebApplicationContext server;

        @Autowired
        public void set(EmbeddedWebApplicationContext server) {
            this.server = server;
        }


        public int port() {
            return server.getEmbeddedServletContainer().getPort();
        }

//        @Value("${local.server.port}")
//        public int port;
    }

    @Configuration
    public static class ChooseAnAvailablePort implements EmbeddedServletContainerCustomizer {

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.setPort(0);
        }
    }
}
