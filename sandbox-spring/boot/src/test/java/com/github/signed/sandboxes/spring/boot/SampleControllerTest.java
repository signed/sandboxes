package com.github.signed.sandboxes.spring.boot;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

import retrofit.client.Response;
import retrofit.http.GET;

public class SampleControllerTest {

    private interface Client {
        @GET("/")
        Response get();

        @GET("/injected")
        Response getInjected();
    }

    @Rule
    public final SpringApplicationRule springApplication = new SpringApplicationRule();

    @Test
    public void can_retrieve_content_from_rest() throws Exception {
        Response response = springApplication.client(Client.class).get();

        assertThat(response.getStatus(), is(200));
        assertThat(response.getBody().mimeType(), containsString("charset=UTF-8"));
        assertThat(readBodyAsUtf8String(response), Matchers.endsWith("Hello World!"));
    }

    @Test
    public void replace_bean_implementation() throws Exception {
        String messageFromCollaborator = readBodyAsUtf8String(this.springApplication.client(Client.class).getInjected());

        assertThat(messageFromCollaborator, is("Hello kind person"));
    }

    public static String readBodyAsUtf8String(Response response) throws IOException {
        return new String(InputStreams.readBytesFrom(response.getBody().in()), "UTF-8");
    }

}