package com.github.signed.sandboxes.spring.advices;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpMethod.POST;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AdvisedControllerApplication.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AdvisedControllerApplication_Test {

    @Value("${local.server.port}")
    int port;

    @Test
    public void callEndpoint() throws Exception {
        assertThat(responseBody(), equalTo("hello world"));
    }

    private String responseBody() {
        ResponseEntity<String> responseTextResponseEntity = response();
        return responseTextResponseEntity.getBody();
    }

    private ResponseEntity<String> response() {
        return new TestRestTemplate().exchange(url(), POST, new HttpEntity<>("world"), String.class);
    }


    private String url() {
        return "http://localhost:" + port;
    }
}