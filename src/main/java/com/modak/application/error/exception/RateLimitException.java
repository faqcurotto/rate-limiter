package com.modak.application.error.exception;

import lombok.Getter;

@Getter
public class RateLimitException extends RuntimeException {

    private final long refillTimeSeconds;

    public RateLimitException(long refillTimeSeconds, String message) {
        super(message);
        this.refillTimeSeconds = refillTimeSeconds;
    }
}
