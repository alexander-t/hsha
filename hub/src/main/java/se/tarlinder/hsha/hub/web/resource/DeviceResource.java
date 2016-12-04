package se.tarlinder.hsha.hub.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.tarlinder.hsha.hub.disruptor.eventhandler.DeviceValueRepository;
import se.tarlinder.hsha.hub.event.json.DeviceEvent;
import se.tarlinder.hsha.hub.service.EventEnqueuerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

@Component
@Path("device")
public class DeviceResource {

    @Autowired
    EventEnqueuerService eventEnqueuerService;

    @Autowired
    DeviceValueRepository deviceValueRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResourceRoot() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceStatus(@PathParam("id") int deviceId) {
        Optional<String> deviceValues = deviceValueRepository.getLastValueFor(deviceId);
        if (deviceValues.isPresent()) {
            return Response.ok(deviceValues.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerDeviceEvent(@PathParam("id") int deviceId, DeviceEvent deviceEvent) {
        eventEnqueuerService.enqueue(deviceEvent);
        return Response.status(Response.Status.OK).build();
    }

}