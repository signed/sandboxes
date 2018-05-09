package com.github.signed.sandbox.spring.resttemplate;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

public class ExceptionAvoidingRestTemplate {
    private final RestTemplate restTemplate = new RestTemplate();

    public ExceptionAvoidingRestTemplate() {
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

    }

    public ClientHttpResponse execute(String url, HttpMethod delete, ResponseExtractor<ClientHttpResponse> responseExtractor) {
        return restTemplate.execute(url, delete, null, responseExtractor);
    }
}
