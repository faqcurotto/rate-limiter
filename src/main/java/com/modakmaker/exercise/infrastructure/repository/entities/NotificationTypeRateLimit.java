package com.modakmaker.exercise.infrastructure.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class NotificationTypeRateLimit {
    @Id
    private String notificationType;
    private String timeUnit;
    private Integer rateLimit;
}
