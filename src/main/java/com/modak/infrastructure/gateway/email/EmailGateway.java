package com.modak.infrastructure.gateway.email;

import org.springframework.stereotype.Service;

@Service
public class EmailGateway {

    public void sendEmail(String userId, String message) {
        System.out.println("Sending message to user " + userId);
    }
}
