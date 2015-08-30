package com.github.signed.sandboxes.spring.boot;

import retrofit.client.Response;

import java.io.IOException;

public class Responses {

    public static String readBodyAsUtf8String(Response response) throws IOException {
        return new String(SampleControllerTest.readBytesFrom(response.getBody().in()), "UTF-8");
    }
}
