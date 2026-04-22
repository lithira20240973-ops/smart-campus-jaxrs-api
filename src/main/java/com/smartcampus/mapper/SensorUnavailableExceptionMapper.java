package com.smartcampus.mapper;

import com.smartcampus.exception.ApiError;
import com.smartcampus.exception.SensorUnavailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    @Override
    public Response toResponse(SensorUnavailableException exception) {
        ApiError error = new ApiError(
            Response.Status.FORBIDDEN.getStatusCode(),
            "Forbidden",
            exception.getMessage(),
            System.currentTimeMillis()
        );
        return Response.status(Response.Status.FORBIDDEN).entity(error).build();
    }
}
