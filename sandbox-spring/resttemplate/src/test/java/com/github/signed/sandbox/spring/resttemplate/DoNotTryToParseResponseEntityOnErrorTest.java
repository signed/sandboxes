package com.github.signed.sandbox.spring.resttemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Spark;

public class DoNotTryToParseResponseEntityOnErrorTest {

    public static class TransferObject{
        public String value;
    }

    public static void main(String[] args) throws Exception {
        new DoNotTryToParseResponseEntityOnErrorTest().setUp();
    }

    @Before
    public void setUp() throws Exception {
        Spark.port(8085);
        Spark.get("/transferobject", "application/json",  (request, response) -> {
            TransferObject result = new TransferObject();
            result.value = "Hello Again";
            response.type("application/json");
            return result;
        }, model -> {
            return new ObjectMapper().writeValueAsString(model);
        });
    }

    @After
    public void tearDown() throws Exception {
        Spark.stop();
    }

    @Test
    public void getWithSpringRestTemplateEntity() throws Exception {
        String url = "http://localhost:8085/transferobject";
        RestTemplate spring = new RestTemplate();
        ResponseEntity<TransferObject> responseEntity = spring.getForEntity(url, TransferObject.class);

        assertThat(responseEntity.getBody().value, is("Hello Again"));
    }
}