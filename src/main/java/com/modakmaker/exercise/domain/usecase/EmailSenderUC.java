package com.modakmaker.exercise.domain.usecase;

import com.modakmaker.exercise.domain.model.NotificationType;
import com.modakmaker.exercise.domain.port.EmailNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderUC {

    private final EmailNotificationPort emailNotificationPort;

    public void sendStatusEmail(String userId, String message) {
        emailNotificationPort.send(NotificationType.STATUS.toString(), userId, message);
    }

    public void sendNewsEmail(String userId, String message) {
        emailNotificationPort.send(NotificationType.NEWS.toString(), userId, message);
    }

    public void sendMarketingEmail(String userId, String message) {
        emailNotificationPort.send(NotificationType.MARKETING.toString(), userId, message);
    }
}
