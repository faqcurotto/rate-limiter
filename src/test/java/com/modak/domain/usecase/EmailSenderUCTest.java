package com.modak.domain.usecase;

import com.modak.domain.model.Email;
import com.modak.domain.port.EmailNotificationPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSenderUCTest {

    @Mock
    EmailNotificationPort emailNotificationPort;

    @Test
    void sendStatusEmail() {
        var email = new Email();
        email.setMessage("message");
        email.setReceiverUserId("BATMAN");
        email.setEmailType("NEWS");

        EmailSenderUC useCase = new EmailSenderUC(emailNotificationPort);

        useCase.sendEmail(email);

        verify(emailNotificationPort, times(1))
                .send(email.getEmailType(), email.getReceiverUserId(), email.getMessage());
    }
}
