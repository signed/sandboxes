package com.github.signed.sandboxes.spring.boot;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

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
        Response response = springApplication.query(rest_client()::get);

        assertThat(response.getStatus(), is(200));
        assertThat(response.getBody().mimeType(), containsString("charset=UTF-8"));
        assertThat(readBodyAsUtf8String(response), Matchers.endsWith("Hello World!"));
    }

    private Client rest_client() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080/")
                .build();
        return restAdapter.create(Client.class);
    }

    @Test
    public void replace_bean_implementation() throws Exception {
        String messageFromCollaborator = this.springApplication.query(() -> readBodyAsUtf8String(rest_client().getInjected()));

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

    public interface Operation<T>{
        T perform() throws Exception;
    }

    private static class SpringApplicationRule extends ExternalResource {

        private final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(BootApplication.class);

        private Optional<ConfigurableApplicationContext> context;

        public <T> T query(Operation<T> operation) throws Exception {

            SpringApplication springApplication = springApplicationBuilder.build();
            context = Optional.of(springApplication.run());

            return operation.perform();
        }

        @Override
        protected void after() {
            if(context.isPresent()){
                SpringApplication.exit(context.get());
            }
        }
    }
}