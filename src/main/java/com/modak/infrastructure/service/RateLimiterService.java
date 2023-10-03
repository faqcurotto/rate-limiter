package com.modak.infrastructure.service;

import com.modak.infrastructure.gateway.db.repository.RateLimitRepository;
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

    public static final String INVALID_EMAIL_TYPE_ERROR_MESSAGE = "Invalid email type";

    private final ProxyManager<String> proxyManager;
    private final RateLimitRepository rateLimitRepository;

    public Bucket resolveBucket(String user, String emailType) {

        final String bucketId = user + "-" + emailType;
        Supplier<BucketConfiguration> bucketConfigSupplier = getBucketConfigSupplier(emailType);
        return proxyManager.builder().build(bucketId, bucketConfigSupplier);
    }

    private Supplier<BucketConfiguration> getBucketConfigSupplier(String emailType) {

        var rateLimitEntity = rateLimitRepository.findById(emailType)
                .orElseThrow(() -> new NoSuchElementException(INVALID_EMAIL_TYPE_ERROR_MESSAGE));

        Refill refill = Refill.intervally(rateLimitEntity.getRateLimit(),
                Duration.of(1, ChronoUnit.valueOf(rateLimitEntity.getTimeUnit())));
        Bandwidth limit = Bandwidth.classic(rateLimitEntity.getRateLimit(), refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}
