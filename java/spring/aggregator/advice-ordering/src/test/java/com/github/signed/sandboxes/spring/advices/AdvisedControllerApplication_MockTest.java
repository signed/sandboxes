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
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {AdvisedControllerApplication.class, AdvisedControllerApplication_MockTest.Config.class})
public class AdvisedControllerApplication_MockTest {

    @Configuration
    static class Config {
        @Bean
        public Reporter reporter() {
            return mock(Reporter.class);
        }

        @Bean
        public BusinessLogic businessLogic() {
            return mock(BusinessLogic.class);
        }
    }

    @Value("${local.server.port}")
    int port;

    @Autowired
    Reporter reporter;

    @Autowired
    BusinessLogic businessLogic;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(reporter);
        Mockito.reset(businessLogic);
    }

    @Test
    public void callEndpoint() throws Exception {
        assertThat(responseBody(), equalTo("hello world"));

        InOrder order = Mockito.inOrder(reporter);
        order.verify(reporter).filterEnter();
        order.verify(reporter).aspectEnter();
        order.verify(reporter).controller();
        order.verify(reporter).aspectExit();
        order.verify(reporter).filterExit();
    }

    @Test
    public void testErrorHandling() throws Exception {
        doThrow(new BusinessException()).when(businessLogic).executeLogic();
        assertThat(response().getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

        InOrder order = Mockito.inOrder(reporter);
        order.verify(reporter).filterEnter();
        order.verify(reporter).aspectEnter();
        order.verify(reporter).controller();
        order.verify(reporter).aspectExit();
        order.verify(reporter).earlierAdvise("BusinessException");
        order.verify(reporter).filterExit();
    }

    @Test
    public void testErrorHandlingInLaterAdvice() throws Exception {
        doThrow(new AnotherBusinessException()).when(businessLogic).executeLogic();
        assertThat(response().getStatusCode(), equalTo(HttpStatus.MOVED_PERMANENTLY));


        InOrder order = Mockito.inOrder(reporter);
        order.verify(reporter).filterEnter();
        order.verify(reporter).aspectEnter();
        order.verify(reporter).controller();
        order.verify(reporter).aspectExit();
        order.verify(reporter).laterAdvise("AnotherBusinessException");
        order.verify(reporter).filterExit();
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