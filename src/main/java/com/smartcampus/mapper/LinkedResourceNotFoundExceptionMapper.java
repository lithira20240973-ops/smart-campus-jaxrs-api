package com.smartcampus.mapper;

import com.smartcampus.exception.ApiError;
import com.smartcampus.exception.LinkedResourceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        ApiError error = new ApiError(
            422, // Unprocessable Entity
            "Unprocessable Entity",
            exception.getMessage(),
            System.currentTimeMillis()
        );
        return Response.status(422).entity(error).build();
    }
}
