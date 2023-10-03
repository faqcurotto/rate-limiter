package com.modak.infrastructure.gateway.db.repository;

import com.modak.infrastructure.gateway.db.entity.NotificationTypeRateLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRepository extends JpaRepository<NotificationTypeRateLimit, String> {
}
