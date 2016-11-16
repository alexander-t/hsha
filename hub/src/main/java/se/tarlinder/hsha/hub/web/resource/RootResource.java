package se.tarlinder.hsha.hub.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Properties;

@Component
@Path("/")
public class RootResource {

    @Autowired
    private ResourceLoader resourceLoader;

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        Resource resource = resourceLoader.getResource("classpath:buildInfo.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
            return String.format("HSHA Hub built %s", (String) properties.get("time"));
        } catch (IOException e) {
            return "HSHA Hub (build info missing!)";
        }
    }
}
