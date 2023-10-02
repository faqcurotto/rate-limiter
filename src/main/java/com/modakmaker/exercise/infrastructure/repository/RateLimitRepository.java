package com.modakmaker.exercise.infrastructure.repository;

import com.modakmaker.exercise.infrastructure.repository.entity.NotificationTypeRateLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRepository extends JpaRepository<NotificationTypeRateLimit, String> {
}
