package com.github.signed.tryanderror.jersey.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/root/sub")
public class AnotherResource {

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello from Another Resource";
    }

}
