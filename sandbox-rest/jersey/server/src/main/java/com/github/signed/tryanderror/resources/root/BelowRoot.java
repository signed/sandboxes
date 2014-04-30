package com.github.signed.tryanderror.resources.root;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

public class BelowRoot {

    private UriInfo uriinfo;

    @Inject
    public BelowRoot(UriInfo uriInfo) {
        uriinfo = uriInfo;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAsPlainText() {
        return "Its me. Really its me from "+uriinfo.getAbsolutePath();
    }
}