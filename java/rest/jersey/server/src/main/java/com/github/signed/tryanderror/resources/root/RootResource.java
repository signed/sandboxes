package com.github.signed.tryanderror.resources.root;

import com.sun.jersey.api.view.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("/")
public class RootResource {

    @GET
    @Produces({MediaType.APPLICATION_XHTML_XML})
    public RootRepresentation getRepresentation(){
        return new RootRepresentation();
    }

    @GET
    @Produces({MediaType.TEXT_HTML})
    public Viewable getRepresentationInHtml(){
        return new Viewable("index.jsp", new RootRepresentation());
    }

    @Path("/below")
    public Object below( @Context UriInfo uriInfo){
        return new BelowRoot(uriInfo);
    }

}