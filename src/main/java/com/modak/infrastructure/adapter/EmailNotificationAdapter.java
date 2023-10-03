package com.modak.infrastructure.adapter;

import com.modak.application.error.exception.RateLimitException;
import com.modak.domain.port.EmailNotificationPort;
import com.modak.infrastructure.service.RateLimiterService;
import com.modak.infrastructure.gateway.email.EmailGateway;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationAdapter implements EmailNotificationPort {

    public static final String RATE_MESSAGE_ERROR_MESSAGE = "Reached notification limit type: %s for user: %s";
    public static final int NANO_TO_SECONDS_PROPORTION = 1000000000;

    private final EmailGateway emailGateway;
    private final RateLimiterService rateLimiterService;

    @Override
    public void send(String type, String userId, String message) {

        ConsumptionProbe probe = consumeToken(type, userId);
        if (probe.isConsumed()) {
            emailGateway.sendEmail(userId, message);
        } else {
            throwRateLimitException(type, userId, probe);
        }
    }

    private ConsumptionProbe consumeToken(String type, String userId) {

        var bucket = rateLimiterService.resolveBucket(userId, type);
        return bucket.tryConsumeAndReturnRemaining(1);
    }

    private static void throwRateLimitException(String type, String userId, ConsumptionProbe probe) {

        long refillTimeSeconds = probe.getNanosToWaitForRefill() / NANO_TO_SECONDS_PROPORTION;
        throw new RateLimitException(refillTimeSeconds, String.format(RATE_MESSAGE_ERROR_MESSAGE, type, userId));
    }
}
