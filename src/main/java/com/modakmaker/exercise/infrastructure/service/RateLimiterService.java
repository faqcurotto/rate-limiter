package com.modakmaker.exercise.infrastructure.service;

import com.modakmaker.exercise.infrastructure.repository.RateLimiterRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

@Service
public class RateLimiterService {

    @Autowired
    private ProxyManager<String> proxyManager;
    @Autowired
    private RateLimiterRepository rateLimiterRepository;

    public Bucket resolveBucket(String user, String notificationType) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(notificationType);

        return proxyManager.builder().build(user + "-" + notificationType, configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(String notificationType) {
        var rateLimit = rateLimiterRepository.findById(notificationType);

        Refill refill = Refill.intervally(rateLimit.get().getRateLimit(),
                Duration.of(1,
                        ChronoUnit.valueOf(rateLimit.get().getTimeUnit())));
        Bandwidth limit = Bandwidth.classic(rateLimit.get().getRateLimit(), refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}
