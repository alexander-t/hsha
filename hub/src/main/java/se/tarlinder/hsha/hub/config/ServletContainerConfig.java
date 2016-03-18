package se.tarlinder.hsha.hub.config;

import org.springframework.boot.context.embedded.*;
import org.springframework.stereotype.Component;

@Component
public class ServletContainerConfig implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8081);
    }
}