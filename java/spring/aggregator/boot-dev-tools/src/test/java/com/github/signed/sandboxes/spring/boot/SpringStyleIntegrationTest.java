package com.github.signed.sandboxes.spring.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit.RestAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BootApplication.class, SpringStyleIntegrationTest.Blub.class})
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class SpringStyleIntegrationTest {


    public static class Blub {
        @Bean(name = ControllerConfiguration.CollaboratorBeanName)
        public Collaborator wadHadeDudeDa() {
            return () -> "replaced in the test";
        }
    }

    @Value("${local.server.port}")
    private int port;

    @Test
    public void returnProductionValue() throws Exception {
        assertThat(Responses.readBodyAsUtf8String(client(Client.class).getInjected()), is("replaced in the test"));
    }

    public <T> T client(Class<T> type) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(String.format("http://localhost:%d", port))
                .build();
        return restAdapter.create(type);
    }
}