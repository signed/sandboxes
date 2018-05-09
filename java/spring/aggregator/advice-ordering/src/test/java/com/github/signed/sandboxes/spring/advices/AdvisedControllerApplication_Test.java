package com.github.signed.sandboxes.spring.advices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {AdvisedControllerApplication.class})
public class AdvisedControllerApplication_Test {

    @Value("${local.server.port}")
    int port;

    @Test
    public void callEndpoint() {
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