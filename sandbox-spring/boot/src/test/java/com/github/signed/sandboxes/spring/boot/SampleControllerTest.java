package com.github.signed.sandboxes.spring.boot;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class SampleControllerTest{

    private interface Client {
        @GET("/")
        Response get();

        @GET("/injected")
        Response getInjected();
    }

    @Test
    public void can_retrieve_content_from_rest() throws Exception {
        SpringApplication springApplication = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(BootApplication.class)
                .build();
        ConfigurableApplicationContext context = springApplication.run();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build();
        Client client = restAdapter.create(Client.class);

        Response response = client.get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getBody().mimeType(), containsString("charset=UTF-8"));
        assertThat(readBodyAsUtf8String(response), Matchers.endsWith("Hello World!"));

        SpringApplication.exit(context);
    }

    private String readBodyAsUtf8String(Response response) throws IOException {
        return new String(readBytesFrom(response.getBody().in()), "UTF-8");
    }

    @Test
    public void replace_bean_implementation() throws Exception {
        SpringApplication springApplication = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(BootApplication.class)
                .build();
        ConfigurableApplicationContext context = springApplication.run();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080/")
                .build();
        Client client = restAdapter.create(Client.class);

        String messageFromCollaborator = readBodyAsUtf8String(client.getInjected());

        assertThat(messageFromCollaborator, is("Hello kind person"));

        SpringApplication.exit(context);
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
}