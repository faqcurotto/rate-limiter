package com.modakmaker.exercise.infrastructure.repository;

import com.modakmaker.exercise.infrastructure.repository.entities.NotificationTypeRateLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RateLimiterRepository extends JpaRepository<NotificationTypeRateLimit, String> {
}
