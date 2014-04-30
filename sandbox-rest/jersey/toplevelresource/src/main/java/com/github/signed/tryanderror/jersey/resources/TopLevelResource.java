package com.github.signed.tryanderror.jersey.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/root")
public class TopLevelResource {

    @Context
    UriInfo uriInfo;

    private final LongRunningProcessStarter processStarter;

    @Inject
    public TopLevelResource(LongRunningProcessStarter processStarter) {
        this.processStarter = processStarter;
    }

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello from the root resource";
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response startLongRunningProcess(@FormParam("numberOfIterations") int numberOfIterations, MultivaluedMap<String, String> formParams) {
    //public Response startLongRunningProcess(@QueryParam("numberOfIterations") int numberOfIterations, @Context UriInfo ui, @Context HttpHeaders hh) {
        ProcessIdentifier identification = processStarter.start(new ProcessInput(numberOfIterations));

        URI uri = UriBuilder.fromResource(LongRunningProcessResource.class).path(identification.toString()).build();
        uri = uriInfo.getBaseUriBuilder().path(LongRunningProcessResource.class).path(identification.toString()).build();
        return Response.created(uri).build();
    }
}