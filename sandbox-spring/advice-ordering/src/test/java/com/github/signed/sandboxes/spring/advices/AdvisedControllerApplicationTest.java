package com.github.signed.sandboxes.spring.advices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpMethod.POST;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AdvisedControllerApplication.class, AdvisedControllerApplicationTest.Config.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AdvisedControllerApplicationTest {

    @Configuration
    static class Config {
        @Bean
        public Reporter reporter() {
            return mock(Reporter.class);
        }
    }

    @Value("${local.server.port}")
    int port;

    @Autowired
    Reporter reporter;

    @Test
    public void callEndpoint() throws Exception {
        ResponseEntity<String> responseTextResponseEntity = new RestTemplate().exchange(url(), POST, new HttpEntity<>("world"), String.class);
        assertThat(responseTextResponseEntity.getBody(), equalTo("hello world"));

        InOrder order = Mockito.inOrder(reporter);
        order.verify(reporter).filterEnter();
        order.verify(reporter).aspectEnter();
        order.verify(reporter).controller();
        order.verify(reporter).aspectExit();
        order.verify(reporter).filterExit();
    }

    private String url() {
        return "http://localhost:" + port;
    }
}