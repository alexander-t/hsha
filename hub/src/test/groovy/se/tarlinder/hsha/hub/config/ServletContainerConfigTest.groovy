package se.tarlinder.hsha.hub.config

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import spock.lang.Specification
import spock.lang.Unroll

class ServletContainerConfigTest extends Specification {

    @Unroll
    def "When -Dport=#portArgument, the port should be #portUsed"() {
        setup:
        def containerMock = Mock(ConfigurableEmbeddedServletContainer)

        when:
        System.setProperty("port", portArgument)
        new ServletContainerConfig().customize(containerMock)

        then:
        1 * containerMock.setPort(portUsed)

        where:
        portArgument | portUsed
        ""           | 8080
        "8080"       | 8080
        "9090"       | 9090
        "eRRoR!"     | 8080
        "65536"      | 8080
    }
}
