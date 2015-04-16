package com.github.signed.sandbox.spring.resttemplate;


import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import spark.Spark;

public class DoNotThrowExceptionsOn4xxReturnCode {

    public static void main(String[] args) throws IOException {
        Spark.port(8085);
        Spark.get("/", (request, response) -> "Hello World");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                throw new UnsupportedOperationException("should never be called");
            }
        });

        ClientHttpResponse response = restTemplate.execute("http://localhost:8085", HttpMethod.DELETE, null, new ResponseExtractor<ClientHttpResponse>() {
            @Override
            public ClientHttpResponse extractData(ClientHttpResponse response) throws IOException {
                return response;
            }
        });

        System.out.println(response.getStatusCode());
        Spark.stop();
    }
}
