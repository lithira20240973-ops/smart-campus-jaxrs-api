package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.repository.RoomRepository;
import com.smartcampus.repository.SensorRepository;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> sensors;
        if (type != null && !type.trim().isEmpty()) {
            sensors = SensorRepository.findByType(type);
        } else {
            sensors = SensorRepository.findAll();
        }
        return Response.ok(sensors).build();
    }

    @POST
    public Response createSensor(Sensor sensor) {
        if (sensor == null || sensor.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(createMessage("Invalid sensor data or missing ID"))
                    .build();
        }

        // Validate that the roomId is provided
        String roomId = sensor.getRoomId();
        if (roomId == null || roomId.trim().isEmpty()) {
             return Response.status(422) // 422 Unprocessable Entity
                    .entity(createMessage("Sensor must have a valid roomId"))
                    .build();
        }

        // Validate that the room actually exists in the room store
        Room room = RoomRepository.findById(roomId);
        if (room == null) {
            return Response.status(422)
                    .entity(createMessage("Validation failed: Room ID '" + roomId + "' does not exist"))
                    .build();
        }

        // Try saving the sensor
        boolean created = SensorRepository.save(sensor);
        if (!created) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(createMessage("Sensor ID already exists"))
                    .build();
        }

        // Successfully created the sensor, link it to the room
        if (room.getSensorIds() != null) {
            room.getSensorIds().add(sensor.getId());
        }

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    @Path("/{sensorId}")
    public Response getSensor(@PathParam("sensorId") String sensorId) {
        Sensor sensor = SensorRepository.findById(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(createMessage("Sensor not found"))
                    .build();
        }
        return Response.ok(sensor).build();
    }

    // Sub-resource locator for sensor readings
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

    // Helper method to create simple JSON messages
    private Map<String, String> createMessage(String text) {
        Map<String, String> map = new HashMap<>();
        map.put("message", text);
        return map;
    }
}
