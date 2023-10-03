package com.modak.domain.port;

public interface EmailNotificationPort {

    void send(String type, String userId, String message);
}
