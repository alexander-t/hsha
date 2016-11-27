package se.tarlinder.hsha.hub.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import se.tarlinder.hsha.hub.web.filter.CORSResponseFilter;
import se.tarlinder.hsha.hub.web.resource.DeviceResource;
import se.tarlinder.hsha.hub.web.resource.RootResource;
import se.tarlinder.hsha.hub.web.resource.SensorResource;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(SensorResource.class);
        register(DeviceResource.class);
        register(RootResource.class);
        register(CORSResponseFilter.class);
    }
}
