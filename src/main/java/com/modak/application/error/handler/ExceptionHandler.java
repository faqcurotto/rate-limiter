package com.modak.application.error.handler;

import com.modak.application.error.exception.RateLimitException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({RateLimitException.class})
    public ResponseEntity<String> handle(RateLimitException e, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("REFILL_TIME_SECONDS", String.valueOf(e.getRefillTimeSeconds()))
                .body("Reached the notification limit: " + e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<String> handle(NoSuchElementException e, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public ResponseEntity<String> handle(Exception e, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
    }
}
