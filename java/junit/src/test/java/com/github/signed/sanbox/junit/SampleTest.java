package com.github.signed.sanbox.junit;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpMethod.POST;

public class SampleTest {


    @BeforeClass
    public static void startServer() throws IOException {
        new TinyHttpServer().startServer();
    }

    @Rule
    public final DemoRequestLog demoRequestLog = new DemoRequestLog();

    @Test
    public void itAllStartsWithHelloWorld() throws Exception {
        TransferObject transferObject = new TransferObject();
        transferObject.one = "one";
        transferObject.two = new Deep();
        transferObject.two.acme = "RoadRunner";

        ResponseEntity<TransferObject> response = issueDomainSpecificRequest(transferObject);

        assertThat(response.getBody().three, equalTo("three"));
    }

    @Test
    public void andGetsBetterFromThereOn() throws Exception {
        TransferObject transferObject = new TransferObject();
        transferObject.one = "1";
        transferObject.two = new Deep();
        transferObject.two.acme = "RoadRunner";

        ResponseEntity<TransferObject> response = issueDomainSpecificRequest(transferObject);

        assertThat(response.getBody().three, equalTo("three"));
    }


    private ResponseEntity<TransferObject> issueDomainSpecificRequest(TransferObject transferObject) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        return DemoRequestLog.logExchangeWith(restTemplate, () -> {
            DemoRequestLog.newRequest("issue domain specific request");
    DemoRequestLog.requestContext("information", "{ \"dafsdf\": \"this is an extra long text to force line wraps into multiple lines but keeps shorter lines together\"}");
            return restTemplate.exchange("http://localhost:8000/test", POST, new HttpEntity<>(transferObject), TransferObject.class);
        });
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
