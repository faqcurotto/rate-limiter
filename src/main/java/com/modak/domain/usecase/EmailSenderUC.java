package com.modak.domain.usecase;

import com.modak.domain.model.Email;
import com.modak.domain.port.EmailNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderUC {

    private final EmailNotificationPort emailNotificationPort;

    public void sendEmail(Email email) {
        emailNotificationPort.send(email.getEmailType(), email.getReceiverUserId(), email.getMessage());
    }
}
