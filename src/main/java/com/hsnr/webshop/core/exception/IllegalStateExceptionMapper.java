package com.hsnr.webshop.core.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

    @Override
    public Response toResponse(IllegalStateException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}