package com.github.signed.sandbox.spring.resttemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import spark.Spark;

public class DoNotThrowExceptionsOn4xxReturnCodeTest {

    @Before
    public void setUp() throws Exception {
        Spark.port(8085);
        Spark.get("/", (request, response) -> "Hello World");Thread.sleep(500);
        Thread.sleep(500);
    }

    @After
    public void tearDown() throws Exception {
        Spark.stop();
    }

    @Test
    public void statusCodeCanBeQueriedFromTheResponse() throws Exception {
        ExceptionAvoidingRestTemplate restTemplate = new ExceptionAvoidingRestTemplate();
        ClientHttpResponse clientHttpResponse = restTemplate.execute("http://localhost:8085", HttpMethod.DELETE, response -> response);

        assertThat(clientHttpResponse.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

}