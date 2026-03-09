package com.aluralatam.forohub.dtos;

public record Response(String message, Object data) {

    public Response(String message) {
        this(message, null);
    }
    
}
