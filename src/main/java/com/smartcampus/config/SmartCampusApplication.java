package com.smartcampus.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/v1")
public class SmartCampusApplication extends ResourceConfig {
    
    public SmartCampusApplication() {
        // Register package scanning to auto-discover resources, mappers, and filters
        packages("com.smartcampus");
        
        // Explicitly register Jackson feature to resolve JSON serialization issues
        register(JacksonFeature.class);
    }
}
