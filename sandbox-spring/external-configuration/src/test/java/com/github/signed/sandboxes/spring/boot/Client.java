package com.github.signed.sandboxes.spring.boot;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Client {
    @GET("/")
    Response get(@Query("propertyKey") String propertyKey);

}
