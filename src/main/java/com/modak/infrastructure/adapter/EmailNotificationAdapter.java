package com.modak.infrastructure.adapter;

import com.modak.application.error.exception.RateLimitException;
import com.modak.domain.port.EmailNotificationPort;
import com.modak.infrastructure.service.RateLimiterService;
import com.modak.infrastructure.gateway.EmailGateway;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationAdapter implements EmailNotificationPort {

    private final EmailGateway emailGateway;
    private final RateLimiterService rateLimiterService;

    @Override
    public void send(String type, String userId, String message) {

        ConsumptionProbe probe = getConsumptionProbe(type, userId);
        if (probe.isConsumed()) {
            emailGateway.sendEmail(userId, message);
        } else {
            throwRateLimitException(type, userId, probe);
        }
    }

    private ConsumptionProbe getConsumptionProbe(String type, String userId) {

        var bucket = rateLimiterService.resolveBucket(userId, type);
        return bucket.tryConsumeAndReturnRemaining(1);
    }

    private static void throwRateLimitException(String type, String userId, ConsumptionProbe probe) {

        long refillTimeSeconds = probe.getNanosToWaitForRefill() / 1000000000;
        String errorMessage = "Reached notification limit type: " + type + " for user: " + userId;
        throw new RateLimitException(refillTimeSeconds, errorMessage);
    }
}
