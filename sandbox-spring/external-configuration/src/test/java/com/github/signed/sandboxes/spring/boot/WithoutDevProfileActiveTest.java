package com.github.signed.sandboxes.spring.boot;

import java.io.IOException;

import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit.RestAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ExternalConfigurationBootApplication.class})
@ActiveProfiles("dev")
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class WithoutDevProfileActiveTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private Environment environment;

    @Test
    public void returnPropertyFromApplicationProperties() throws Exception {
        assertThat(environment.getProperty("application.global"), is("overlord"));
    }

    @Test
    public void returnProductionValue() throws Exception {
        propertyJsonFor("only.present.in.dev").assertThat("$.['only.present.in.dev']", is("wtf"));
    }

    @Test
    public void checkOverriddenPropertyIsPresent() throws Exception {
        propertyJsonFor("overridden.in.dev").assertThat("$.['overridden.in.dev']", is("itwonthurt"));
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