package com.github.signed.sanbox.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

public class SampleTest {

    static final java.lang.String DEMO_OUTPUT = "demo-output";

    @Rule
    public PrintTestMethodNameRule printTestMethodNameRule = new PrintTestMethodNameRule();

    @Test
    public void itAllStartsWithHelloWorld() throws Exception {
        Logger logger = Logger.getLogger(DEMO_OUTPUT);

        String message = CaseFormat.SENTENCE.to(CaseFormat.LOWER_CAMEL, "here we go into the other direction");
        logger.warn(message);

        new TinyHttpServer().startServer();

        TransferObject transferObject = new TransferObject();
        transferObject.one = "one";
        transferObject.two = new Deep();
        transferObject.two.acme = "RoadRunner";

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transferObject);
        logger.info(json);

        String url = "http://localhost:8000/test";


        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        ResponseEntity<TransferObject> response = restTemplate.exchange(url, POST, new HttpEntity<>(transferObject), TransferObject.class);
        //ResponseEntity<TransferObject> response = restTemplate.exchange("http://localhost:8000/test", POST, new HttpEntity<>(json), TransferObject.class);


        assertThat(response.getBody().three, equalTo("three"));
    }

    public static class TransferObject {
        public String one;
        public Deep two;
        public String three;
    }

    public static class Deep {
        public String acme;
        public String coyote;
    }

}
