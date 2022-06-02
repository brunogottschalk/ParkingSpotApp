package com.parkingSpot.app.exceptions;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> noHistoryFound() {
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "no history found");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> internalServerError(Exception ex) {
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
