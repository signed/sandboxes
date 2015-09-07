package com.github.signed.sandboxes.spring.boot;

import retrofit.client.Response;
import retrofit.http.GET;

public interface Client {
    @GET("/")
    Response get();
}
