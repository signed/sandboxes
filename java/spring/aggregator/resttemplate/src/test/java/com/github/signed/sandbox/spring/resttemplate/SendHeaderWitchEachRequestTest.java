package com.github.signed.sandbox.spring.resttemplate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import spark.Spark;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SendHeaderWitchEachRequestTest {

    private final List<String> headerValue = new ArrayList<>();

    @Before
    public void startSpark() throws Exception {
        Spark.port(8086);
        Spark.get("/*", (request, response) -> {
            headerValue.add(request.headers("X-Custom-Header"));
            response.status(200);
            return "";
        });
        Spark.awaitInitialization();
    }

    @After
    public void stopSpark() throws Exception {
        SparkSupport.sparkStop();
    }

    @Test
    public void serverReceivesTheCustomHeader() throws Exception {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
                connection.addRequestProperty("X-Custom-Header", "Hello");
            }
        };

        new RestTemplate(requestFactory).getForEntity("http://localhost:8086", String.class);

        assertThat(headerValue.get(0), equalTo("Hello"));
    }
}
