package com.modak.infrastructure.service;

import com.modak.infrastructure.repository.RateLimitRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final ProxyManager<String> proxyManager;
    private final RateLimitRepository rateLimitRepository;

    public Bucket resolveBucket(String user, String emailType) {

        final String userNotificationTypeId = user + "-" + emailType;
        Supplier<BucketConfiguration> bucketConfigSupplier = getBucketConfigSupplier(emailType);
        return proxyManager.builder().build(userNotificationTypeId, bucketConfigSupplier);
    }

    private Supplier<BucketConfiguration> getBucketConfigSupplier(String emailType) {

        var rateLimitEntity = rateLimitRepository.findById(emailType)
                .orElseThrow(() -> new NoSuchElementException("Invalid email type"));

        Refill refill = Refill.intervally(rateLimitEntity.getRateLimit(),
                Duration.of(1, ChronoUnit.valueOf(rateLimitEntity.getTimeUnit())));
        Bandwidth limit = Bandwidth.classic(rateLimitEntity.getRateLimit(), refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}
