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

        Response putResponse = client.get();

        assertThat(putResponse.getStatus(), is(200));
        assertThat(putResponse.getBody().mimeType(), containsString("charset=UTF-8"));
        assertThat(new String(readBytesFrom(putResponse.getBody().in()), "UTF-8"), Matchers.endsWith("Hello World!"));

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