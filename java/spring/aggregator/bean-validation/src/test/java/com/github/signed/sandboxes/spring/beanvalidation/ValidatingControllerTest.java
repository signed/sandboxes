package com.github.signed.sandboxes.spring.beanvalidation;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.PUT;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidatingControllerTest {

    private interface Client {
        @PUT("/")
        Response put(@Body TransferObject transferObject);
    }

    @Test(expected = RuntimeException.class)
    public void testName() throws Exception {
        SpringApplication springApplication = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(BootWithBeanValidationApplication.class)
                .build();
        ConfigurableApplicationContext context = springApplication.run();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .build();
        Client client = restAdapter.create(Client.class);

        Response putResponse = client.put(new TransferObject());

        assertThat(putResponse.getStatus(), is(400));

        SpringApplication.exit(context);
    }
}