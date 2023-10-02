package com.modakmaker.exercise.application.errors.exceptions;

import lombok.Getter;

@Getter
public class RateLimitException extends RuntimeException {

    private long refillTimeSeconds;

    public RateLimitException(long refillTimeSeconds, String message) {
        super(message);
        this.refillTimeSeconds = refillTimeSeconds;
    }
}
