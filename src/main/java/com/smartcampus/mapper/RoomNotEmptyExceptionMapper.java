package com.smartcampus.mapper;

import com.smartcampus.exception.ApiError;
import com.smartcampus.exception.RoomNotEmptyException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        ApiError error = new ApiError(
            Response.Status.CONFLICT.getStatusCode(),
            "Conflict",
            exception.getMessage(),
            System.currentTimeMillis()
        );
        return Response.status(Response.Status.CONFLICT).entity(error).build();
    }
}
