package com.github.signed.sandboxes.spring.beanvalidation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootWithBeanValidationApplication.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class SpringStyleIntegrationTest {

    private final RestTemplate restTemplate = new TestRestTemplate();
    @Value("${local.server.port}")
    private int port;

    private final Map<String, Object> parameters = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        parameters.put("port", port);

    }

    @Test
    public void ifNameIsMissingRespondWith400() throws Exception {
        TransferObject transferObject = new TransferObject();
        HttpEntity<?> requestEntity = new HttpEntity<>(transferObject);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:{port}/primitives", HttpMethod.PUT, requestEntity, String.class, parameters);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void ensureCustomValidatorIsHookedUp() throws Exception {
        PhoneNumberTransferObjectForClient transferObject = new PhoneNumberTransferObjectForClient();
        transferObject.special = "no mater what";

        HttpEntity<?> requestEntity = new HttpEntity<>(transferObject);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:{port}/specialvalidator", HttpMethod.PUT, requestEntity, String.class, parameters);
        System.out.println(response.getBody());

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
