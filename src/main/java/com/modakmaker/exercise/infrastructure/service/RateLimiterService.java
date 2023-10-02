package com.modakmaker.exercise.infrastructure.service;

import com.modakmaker.exercise.infrastructure.repository.RateLimitRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final ProxyManager<String> proxyManager;
    private final RateLimitRepository rateLimitRepository;

    public Bucket resolveBucket(String user, String notificationType) {

        var userNotificationTypeId = user + "-" + notificationType;
        Supplier<BucketConfiguration> bucketConfigSupplier = getBucketConfigSupplier(notificationType);
        return proxyManager.builder().build(userNotificationTypeId, bucketConfigSupplier);
    }

    private Supplier<BucketConfiguration> getBucketConfigSupplier(String notificationType) {

        var rateLimitEntity = rateLimitRepository.findById(notificationType).orElseThrow();

        Refill refill = Refill.intervally(rateLimitEntity.getRateLimit(),
                Duration.of(1, ChronoUnit.valueOf(rateLimitEntity.getTimeUnit())));
        Bandwidth limit = Bandwidth.classic(rateLimitEntity.getRateLimit(), refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}
