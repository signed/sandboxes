package com.github.signed.tryanderror.jersey.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/longrunningprocess")
public class LongRunningProcessResource {

    private final ProcessArchive archive;

    @Inject
    public LongRunningProcessResource(ProcessArchive archive){
        this.archive = archive;
    }

    @GET
    @Produces("text/plain")
    public String list() {
        final StringBuilder builder = new StringBuilder();
        archive.eachProcess(new Callback<LongRunningProcess>() {
            @Override
            public void call(LongRunningProcess value) {
                builder.append(value.identifier).append("\n");
            }
        });
        return builder.toString();
    }

    @GET
    @Produces("text/plain")
    @Path("{id}")
    public String single(@PathParam("id") long id) {
        final StringBuilder builder = new StringBuilder();
        final ProcessIdentifier identifier = new ProcessIdentifier(id);
        archive.eachProcess(new Callback<LongRunningProcess>() {
            @Override
            public void call(LongRunningProcess value) {
                if(identifier.equals(value.identifier)) {
                    builder.append(value.toString());
                }
            }
        });

        String message = builder.toString();
        if (message.isEmpty()){
           throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("'"+id +"' does not identify a long running process").build());
        }
        return message;
    }

}
