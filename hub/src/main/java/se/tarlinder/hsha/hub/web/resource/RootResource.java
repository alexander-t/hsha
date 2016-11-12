package se.tarlinder.hsha.hub.web.resource;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/")
public class RootResource {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "HSHA Hub up and running";
    }
}
