package com.modak.infrastructure.service;

import com.modak.infrastructure.gateway.db.repository.RateLimitRepository;
import com.modak.infrastructure.gateway.db.entity.NotificationTypeRateLimit;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.distributed.proxy.RemoteBucketBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateLimitServiceTest {

    @Mock
    ProxyManager<String> proxyManager;
    @Mock
    RateLimitRepository rateLimitRepository;
    @Mock
    RemoteBucketBuilder<String> remoteBucketBuilder;
    @Mock
    BucketProxy bucket;

    @Test
    void sendOK() {
        String type = "STATUS";
        String userId = "BATMAN";
        NotificationTypeRateLimit rateLimit = new NotificationTypeRateLimit();
        rateLimit.setRateLimit(2);
        rateLimit.setTimeUnit("HOURS");
        rateLimit.setNotificationType("NEWS");

        when(rateLimitRepository.findById(type)).thenReturn(Optional.of(rateLimit));
        when(proxyManager.builder()).thenReturn(remoteBucketBuilder);
        when(remoteBucketBuilder.build(eq(userId + "-" + type), any(Supplier.class))).thenReturn(bucket);

        RateLimiterService service = new RateLimiterService(proxyManager, rateLimitRepository);
        var result = service.resolveBucket(userId, type);

        assertEquals(bucket, result);
    }
}
