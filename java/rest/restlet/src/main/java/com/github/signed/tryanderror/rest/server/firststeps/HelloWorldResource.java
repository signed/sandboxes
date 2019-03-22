package com.github.signed.tryanderror.rest.server.firststeps;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.ws.rs.core.Response;

public class HelloWorldResource extends ServerResource {

    @Get
    public String represent() {
        return "hello, world";
    }
    
    //@Post
    public Response  createNewNode() {
        return Response.created(null).build();
    }
}