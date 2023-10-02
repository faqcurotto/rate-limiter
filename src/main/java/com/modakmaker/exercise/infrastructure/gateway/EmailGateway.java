package com.modakmaker.exercise.infrastructure.gateway;

import org.springframework.stereotype.Service;

@Service
public class EmailGateway {

    public void sendEmail(String userId, String message) {
        System.out.println("Sending message to user " + userId);
    }
}
