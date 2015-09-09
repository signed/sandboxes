package com.github.signed.sandboxes.spring.boot;

import java.io.IOException;

import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit.RestAdapter;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ExternalConfigurationBootApplication.class})
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class WithoutAnActiveProfileTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void returnProductionValue() throws Exception {
        String propertyKey = "application.global";
        JsonAsserter with = propertyJsonFor(propertyKey);
        with.assertThat("$.['application.global']", is("overlord"));
    }

    private JsonAsserter propertyJsonFor(String propertyKey) throws IOException {
        String json = Responses.readBodyAsUtf8String(client(Client.class).get(propertyKey));
        return JsonAssert.with(json);
    }

    public <T> T client(Class<T> type) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(String.format("http://localhost:%d", port))
                .build();
        return restAdapter.create(type);
    }
}