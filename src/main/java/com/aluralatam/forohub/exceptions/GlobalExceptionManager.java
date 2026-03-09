package com.aluralatam.forohub.exceptions;

import com.aluralatam.forohub.dtos.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionManager {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error de formato en la solicitud"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error interno del servidor"));
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Response> handleCustomException(CustomNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(ex.getMessage()));
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<Response> handleCustomException(CustomBadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(ex.getMessage()));
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<Response> handleCustomException(CustomForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(ex.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new Response("Método HTTP no permitido"));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(ex.getMessage()));
    }
    // @ExceptionHandler(NotFoundException.class)
    // public ResponseEntity<Response> handleNotFoundException(NotFoundException ex)
    // {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
    // Response(ex.getMessage()));
    // }

}
