package se.tarlinder.hsha.hub.config;

import org.springframework.boot.context.embedded.*;
import org.springframework.stereotype.Component;

@Component
public class ServletContainerConfig implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(determineContainerPort());
    }

    private int determineContainerPort() {
        final int defaultPortNumber = 8080;
        final int maxPortNumber = 65535;

        int port = defaultPortNumber;
        try {
            port = Integer.parseInt(System.getProperty("port", defaultPortNumber + ""));
        } catch (NumberFormatException e) {
            // Do nothing. Default value is set.
        }

        if (port > maxPortNumber) {
            port = defaultPortNumber;
        }
        return port;
    }
}