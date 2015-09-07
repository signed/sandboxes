package com.github.signed.sandboxes.spring.boot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import retrofit.RestAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PropertiesFromConfigurationFileApplication.class})
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class SpringStyleIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void hasCommitHash() throws Exception {
        assertThat(versionInformation().commit_hash, isA(String.class));
    }

    @Test
    public void hasHardCodedBuildNumber() throws Exception {
        assertThat(versionInformation().build_number, is("45"));
    }

    @Test
    public void hasCurrentVersion() throws Exception {
        assertThat(versionInformation().version, is("0.1.0-SNAPSHOT"));
    }

    @Test
    public void hasBuildTimeStamp() throws Exception {
        assertThat(versionInformation().timestamp, isA(String.class));
    }

    private ApplicationVersionTO versionInformation() {
        return client(Client.class).get();
    }

    public <T> T client(Class<T> type) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(String.format("http://localhost:%d", port))
                .build();
        return restAdapter.create(type);
    }
}