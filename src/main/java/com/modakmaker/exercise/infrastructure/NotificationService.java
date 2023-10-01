package com.modakmaker.exercise.infrastructure;

public interface NotificationService {

    void send(String type, String userId, String message);
}
