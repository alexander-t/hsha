package se.tarlinder.hsha.hub.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.tarlinder.hsha.hub.disruptor.eventhandler.SensorValueRepository;
import se.tarlinder.hsha.hub.event.json.SensorEvent;
import se.tarlinder.hsha.hub.service.EventEnqueuerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Map;
import java.util.Optional;

@Component
@Path("sensor")
public class SensorResource {

    @Autowired
    EventEnqueuerService eventEnqueuerService;

    @Autowired
    SensorValueRepository sensorValueRepository;

    // Mostly for smoke testing
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResourceRoot() {
        return Response.status(Status.OK).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorValue(@PathParam("id") int sensorId) {
        Optional<Map<String, String>> sensorValues = sensorValueRepository.getLastValueFor(sensorId);
        if (sensorValues.isPresent()) {
            return Response.ok(sensorValues.get()).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerSensorEvent(@PathParam("id") int sensorId, SensorEvent sensorEvent) {
        eventEnqueuerService.enqueue(sensorEvent);
        return Response.status(Status.OK).build();
    }

}