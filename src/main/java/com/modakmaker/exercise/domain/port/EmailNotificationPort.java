package com.modakmaker.exercise.domain.port;

public interface EmailNotificationPort {

    void send(String type, String userId, String message);
}
