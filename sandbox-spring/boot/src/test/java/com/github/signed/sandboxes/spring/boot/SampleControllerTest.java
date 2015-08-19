package com.github.signed.sandboxes.spring.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;

public class SampleControllerTest {

    private interface Client {
        @GET("/")
        Response get();

        @GET("/injected")
        Response getInjected();
    }

    @Rule
    public final SpringApplicationRule springApplication = new SpringApplicationRule();

    @Test
    public void can_retrieve_content_from_rest() throws Exception {
        Response response = springApplication.client(Client.class).get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getBody().mimeType(), containsString("charset=UTF-8"));
        assertThat(readBodyAsUtf8String(response), Matchers.endsWith("Hello World!"));
    }

    @Test
    public void replace_bean_implementation() throws Exception {
        String messageFromCollaborator = readBodyAsUtf8String(this.springApplication.client(Client.class).getInjected());

        assertThat(messageFromCollaborator, is("Hello kind person"));
    }

    private static String readBodyAsUtf8String(Response response) throws IOException {
        return new String(readBytesFrom(response.getBody().in()), "UTF-8");
    }

    public static byte[] readBytesFrom(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int r = inputStream.read(buffer);
            if (r == -1) break;
            out.write(buffer, 0, r);
        }

        return out.toByteArray();
    }

    @Component
    public static class RuleConfiguration {
        @Autowired
        public EmbeddedWebApplicationContext server;


        public int port() {
            return server.getEmbeddedServletContainer().getPort();
        }

//        @Value("${local.server.port}")
//        public int port;
    }

    private static class SpringApplicationRule extends ExternalResource {

        private final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(BootApplication.class);

        private final RuleConfiguration ruleConfiguration = new RuleConfiguration();
        private Optional<ConfigurableApplicationContext> context = Optional.empty();

        public <T> T client(Class<T> type){
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
            if(context.isPresent()){
                return;
            }
            SpringApplication springApplication = springApplicationBuilder.build();
            ConfigurableApplicationContext configurableContext = springApplication.run();

            ConfigurableListableBeanFactory beanFactory = configurableContext.getBeanFactory();
            beanFactory.initializeBean(ruleConfiguration, "ruleConfiguration");
            beanFactory.autowireBean(ruleConfiguration);

            context = Optional.of(configurableContext);
        }
    }
}