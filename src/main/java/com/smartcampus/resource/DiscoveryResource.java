package com.smartcampus.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDiscovery() {
        Map<String, Object> response = new HashMap<>();
        response.put("api name", "Smart Campus API");
        response.put("version", "1.0");
        response.put("admin contact", "admin@smartcampus.local");
        
        Map<String, String> resources = new HashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        
        response.put("resources", resources);
        
        return response;
    }
}
