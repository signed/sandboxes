package jersey;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RootResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello World " + new Date().getTime();
    }

    @Path("/below")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String below() {
        return "below";
    }
}