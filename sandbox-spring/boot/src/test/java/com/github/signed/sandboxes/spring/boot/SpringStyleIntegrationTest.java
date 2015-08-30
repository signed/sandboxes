package com.github.signed.sandboxes.spring.boot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import retrofit.RestAdapter;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApplication.class)
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
    public void returnProductionValue() throws Exception {
        assertThat(Responses.readBodyAsUtf8String(client(Client.class).getInjected()), is("Hello kind person"));
    }

    public <T> T client(Class<T> type) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:"+port)
                .build();
        return restAdapter.create(type);
    }
}