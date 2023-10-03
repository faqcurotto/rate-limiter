package com.modak.infrastructure.repository;

import com.modak.infrastructure.repository.entity.NotificationTypeRateLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRepository extends JpaRepository<NotificationTypeRateLimit, String> {
}
