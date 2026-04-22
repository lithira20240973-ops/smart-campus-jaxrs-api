package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.repository.RoomRepository;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public List<Room> getAllRooms() {
        return RoomRepository.findAll();
    }

    @POST
    public Response createRoom(Room room) {
        if (room == null || room.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(createMessage("Invalid room data or missing ID"))
                    .build();
        }

        boolean created = RoomRepository.save(room);
        if (!created) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(createMessage("Room ID already exists"))
                    .build();
        }

        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = RoomRepository.findById(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(createMessage("Room not found"))
                    .build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = RoomRepository.findById(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(createMessage("Room not found"))
                    .build();
        }

        // Prevent deletion if room has associated sensors
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(createMessage("Cannot delete room: it has active sensors"))
                    .build();
        }

        RoomRepository.delete(roomId);
        return Response.ok(createMessage("Room deleted successfully")).build();
    }

    // Helper method to create simple JSON messages instead of raw strings
    private Map<String, String> createMessage(String text) {
        Map<String, String> map = new HashMap<>();
        map.put("message", text);
        return map;
    }
}
