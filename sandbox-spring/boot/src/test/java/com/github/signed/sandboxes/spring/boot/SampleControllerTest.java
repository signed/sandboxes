package com.github.signed.sandboxes.spring.boot;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;

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

        SpringApplication.exit(context);
    }
}