package se.tarlinder.hsha.hub.web.filter

import spock.lang.Specification

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.core.MultivaluedHashMap

class CORSResponseFilterTest extends Specification {
    def "Access-Control-* headers are added to responses"() {
        setup:
        def responseStub = Stub(ContainerResponseContext)
        def headers = new MultivaluedHashMap()
        responseStub.getHeaders() >> headers

        when:
        new CORSResponseFilter().filter(Stub(ContainerRequestContext.class), responseStub)

        then:
        headers["Access-Control-Allow-Origin"] == ["*"]
        headers["Access-Control-Allow-Methods"] == ["GET, POST, DELETE, PUT"]

    }
}
