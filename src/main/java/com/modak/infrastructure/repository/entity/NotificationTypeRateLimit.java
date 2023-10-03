package com.modak.infrastructure.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class NotificationTypeRateLimit {
    @Id
    private String notificationType;
    private String timeUnit;
    private Integer rateLimit;
}
