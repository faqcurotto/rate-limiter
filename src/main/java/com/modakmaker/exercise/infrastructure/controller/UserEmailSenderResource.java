package com.modakmaker.exercise.infrastructure.controller;

import com.modakmaker.exercise.domain.model.UserEmail;
import com.modakmaker.exercise.domain.usecase.UserEmailSenderUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserEmailSenderResource {

    private final UserEmailSenderUC userEmailSenderUC;

    @PostMapping(value = "/news-email-sender")
    public ResponseEntity<String> sendNewsEmail(@RequestBody UserEmail userEmail) {

        userEmailSenderUC.sendNewsEmail(userEmail.getUserId(), userEmail.getMessage());
        return ResponseEntity.ok("");

    }

    @PostMapping(value = "/status-email-sender")
    public ResponseEntity<String> sendStatusEmail(@RequestBody UserEmail userEmail) {

        userEmailSenderUC.sendStatusEmail(userEmail.getUserId(), userEmail.getMessage());
        return ResponseEntity.ok("");

    }
}
