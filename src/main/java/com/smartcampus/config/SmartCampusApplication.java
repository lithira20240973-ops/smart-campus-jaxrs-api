package com.smartcampus.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api/v1")
public class SmartCampusApplication extends Application {
    // JAX-RS will automatically discover and register all root resource classes
    // and providers in the classpath.
}
