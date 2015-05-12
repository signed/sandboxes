package com.github.signed.sandbox.spring.resttemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Spark;

public class DoNotTryToParseResponseEntityOnErrorTest {

    public static class TransferObject {
        public String value;
    }

    public static void main(String[] args) throws Exception {
        new DoNotTryToParseResponseEntityOnErrorTest().setUp();
    }

    @Before
    public void setUp() throws Exception {
        Spark.port(8085);
        Spark.get("/transferobject", (request, response) -> {
            TransferObject result = new TransferObject();
            result.value = "Hello Again";
            response.type("application/json");
            return result;
        }, model -> {
            //return model.toString();
            return new ObjectMapper().writeValueAsString(model);
        });

        Thread.sleep(500);
    }

    @After
    public void tearDown() throws Exception {
        Spark.stop();
    }

    private final String url = "http://localhost:8085/transferobject";
    private final RestTemplate spring = new RestTemplate();

    @Test
    public void getWithSpringRestTemplateEntity() throws Exception {

        ResponseEntity<TransferObject> responseEntity = spring.getForEntity(url, TransferObject.class);

        assertThat(responseEntity.getBody().value, is("Hello Again"));
    }

    @Test
    public void getWithSpringRestTemplateExchange() throws Exception {
        ResponseEntity<TransferObject> responseEntity = spring.exchange(url, HttpMethod.GET, null, TransferObject.class);

        assertThat(responseEntity.getBody().value, is("Hello Again"));
    }
}