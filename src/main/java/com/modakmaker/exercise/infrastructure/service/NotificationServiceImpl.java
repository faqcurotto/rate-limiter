package com.modakmaker.exercise.infrastructure.service;

import com.modakmaker.exercise.infrastructure.NotificationService;
import com.modakmaker.exercise.infrastructure.gateway.Gateway;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RateLimiterService rateLimiterService;
    @Override
    public void send(String type, String userId, String message) {

        var bucket = rateLimiterService.resolveBucket(userId, type);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (!probe.isConsumed()) {
            throw new RuntimeException("sdfsdf");
        }
//        if (probe.isConsumed()) {
//            Collections.shuffle(PLANETS);
//            return ResponseEntity.ok().header(X_RATE_TOKEN_AVAILABLE, Long.toString(probe.getRemainingTokens()))
//                    .body(PLANETS.get(0));
//        } else {
//            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
//                    .header(X_RATE_REFILL_TIME, Long.toString(probe.getNanosToWaitForRefill() / 1_000_000_000)).build();
//        }
    }
}
