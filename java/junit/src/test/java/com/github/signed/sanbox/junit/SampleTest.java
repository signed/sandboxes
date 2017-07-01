package com.github.signed.sanbox.junit;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpMethod.POST;

public class SampleTest {

    @Rule
    public final DemoRequestLog demoRequestLog = new DemoRequestLog();

    @Test
    public void itAllStartsWithHelloWorld() throws Exception {
        new TinyHttpServer().startServer();

        TransferObject transferObject = new TransferObject();
        transferObject.one = "one";
        transferObject.two = new Deep();
        transferObject.two.acme = "RoadRunner";

        ResponseEntity<TransferObject> response = issueDomainSpecificRequest(transferObject);

        assertThat(response.getBody().three, equalTo("three"));
    }

    private ResponseEntity<TransferObject> issueDomainSpecificRequest(TransferObject transferObject) {
        DemoRequestLog.newRequest("issue domain specific request");
        DemoRequestLog.requestContext("information", "{ \"dafsdf\": null}");

        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.getInterceptors().add(new DemoRequestLog.LoggingRequestInterceptor());
        return restTemplate.exchange("http://localhost:8000/test", POST, new HttpEntity<>(transferObject), TransferObject.class);
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
