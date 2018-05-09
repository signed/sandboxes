package com.github.signed.sandboxes.spring.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit.RestAdapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,classes = {PropertiesFromConfigurationFileApplication.class})
public class SpringStyleIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void hasCommitHash() {
        assertThat(versionInformation().commit_hash, isA(String.class));
    }

    @Test
    public void hasHardCodedBuildNumber() {
        assertThat(versionInformation().build_number, is("45"));
    }

    @Test
    public void hasCurrentVersion() {
        assertThat(versionInformation().version, is("0.1.0-SNAPSHOT"));
    }

    @Test
    public void hasBuildTimeStamp() {
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