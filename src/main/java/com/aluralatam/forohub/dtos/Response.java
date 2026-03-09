package com.aluralatam.forohub.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response(String message, Object data) {

    public Response(String message) {
        this(message, null);
    }
    
}
