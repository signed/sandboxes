package com.github.signed.sandboxes.spring.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import retrofit.RestAdapter;
import retrofit.client.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class SampleControllerTest {

    @Rule
    public final SpringApplicationRule springApplication = new SpringApplicationRule();

    @Test
    public void can_retrieve_content_from_rest() throws Exception {
        Response response = springApplication.client(Client.class).get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getBody().mimeType(), containsString("charset=UTF-8"));
        assertThat(Responses.readBodyAsUtf8String(response), Matchers.endsWith("Hello World!"));
    }

    @Test
    public void replace_bean_implementation() throws Exception {
        String messageFromCollaborator = Responses.readBodyAsUtf8String(this.springApplication.client(Client.class).getInjected());

        assertThat(messageFromCollaborator, is("Hello kind person"));
    }

    private static String readBodyAsUtf8String(Response response) throws IOException {
        return new String(readBytesFrom(response.getBody().in()), "UTF-8");
    }

}