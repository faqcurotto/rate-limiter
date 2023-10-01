package com.modakmaker.exercise.domain.usecase;

import com.modakmaker.exercise.domain.model.NotificationType;
import com.modakmaker.exercise.infrastructure.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEmailSenderUC {

    private final NotificationService notificationService;

    public void sendStatusEmail(String userId, String message) {
        notificationService.send(NotificationType.STATUS.toString(), userId, message);
    }

    public void sendNewsEmail(String userId, String message) {
        notificationService.send(NotificationType.NEWS.toString(), userId, message);
    }

    public void sendMarketingEmail(String userId, String message) {
        notificationService.send(NotificationType.STATUS.toString(), userId, message);
    }
}
