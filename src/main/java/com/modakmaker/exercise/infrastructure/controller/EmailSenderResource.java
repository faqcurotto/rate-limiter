package com.modakmaker.exercise.infrastructure.controller;

import com.modakmaker.exercise.domain.model.Email;
import com.modakmaker.exercise.domain.usecase.EmailSenderUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailSenderResource {

    private final EmailSenderUC emailSenderUC;

    @PostMapping(value = "/news-email-sender")
    public ResponseEntity<String> sendNewsEmail(@RequestBody Email email) {

        emailSenderUC.sendNewsEmail(email.getReceiverUserId(), email.getMessage());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/status-email-sender")
    public ResponseEntity<String> sendStatusEmail(@RequestBody Email email) {

        emailSenderUC.sendStatusEmail(email.getReceiverUserId(), email.getMessage());
        return ResponseEntity.ok().build();

    }

    @PostMapping(value = "/marketing-email-sender")
    public ResponseEntity<String> sendMarketingEmail(@RequestBody Email email) {

        emailSenderUC.sendMarketingEmail(email.getReceiverUserId(), email.getMessage());
        return ResponseEntity.ok().build();
    }
}
