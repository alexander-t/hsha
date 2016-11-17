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
    @Produces(MediaType.APPLICATION_JSON)
    public String index() {
        return String.format("{application: 'HSHA Hub', build-time: %s}", getBuildTime());
    }

    private String getBuildTime() {
        Resource resource = resourceLoader.getResource("classpath:buildInfo.properties");
        Properties properties = new Properties();
        String buildTime;
        try {
            properties.load(resource.getInputStream());
            buildTime = properties.getProperty("time", "unset");
            if ("".equals(buildTime)) {
                buildTime = "unset";
            }
        } catch (IOException e) {
            buildTime = "#error getting build time";
        }
        return buildTime;
    }
}
