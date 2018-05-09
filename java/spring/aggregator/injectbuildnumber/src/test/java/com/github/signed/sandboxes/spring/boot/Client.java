package com.github.signed.sandboxes.spring.boot;

import retrofit.http.GET;

public interface Client {
    @GET("/")
    ApplicationVersionTO get();
}
