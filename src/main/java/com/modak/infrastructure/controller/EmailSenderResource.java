package com.modak.infrastructure.controller;

import com.modak.domain.model.Email;
import com.modak.domain.usecase.EmailSenderUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailSenderResource {

    private final EmailSenderUC emailSenderUC;

    @PostMapping(value = "/email-sender")
    public ResponseEntity<String> sendEmail(@RequestBody Email email) {

        emailSenderUC.sendEmail(email);
        return ResponseEntity.ok().build();
    }
}
