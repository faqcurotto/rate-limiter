package com.modak.infrastructure.adapter;

import com.modak.application.error.exception.RateLimitException;
import com.modak.infrastructure.gateway.EmailGateway;
import com.modak.infrastructure.service.RateLimiterService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailNotificationAdapterTest {

    @Mock
    EmailGateway emailGateway;
    @Mock
    RateLimiterService rateLimiterService;
    @Mock
    Bucket bucket;
    @Mock
    ConsumptionProbe consumptionProbe;

    @Test
    void sendOK() {
        String type = "STATUS";
        String userId = "BATMAN";
        String message = "Your BatAccount was verified.";

        when(rateLimiterService.resolveBucket(userId, type)).thenReturn(bucket);
        when(bucket.tryConsumeAndReturnRemaining(1)).thenReturn(consumptionProbe);
        when(consumptionProbe.isConsumed()).thenReturn(true);

        EmailNotificationAdapter adapter = new EmailNotificationAdapter(emailGateway, rateLimiterService);

        adapter.send(type, userId, message);

        verify(emailGateway, times(1)).sendEmail(userId, message);
    }

    @Test
    void sendFAIL() {
        String type = "STATUS";
        String userId = "BATMAN";
        String message = "Your BatAccount was verified.";

        when(rateLimiterService.resolveBucket(userId, type)).thenReturn(bucket);
        when(bucket.tryConsumeAndReturnRemaining(1)).thenReturn(consumptionProbe);
        when(consumptionProbe.isConsumed()).thenReturn(false);
        when(consumptionProbe.getNanosToWaitForRefill()).thenReturn(10000000000L);

        EmailNotificationAdapter adapter = new EmailNotificationAdapter(emailGateway, rateLimiterService);

        RateLimitException exception = assertThrows(RateLimitException.class, () -> adapter.send(type, userId, message));

        assertEquals(10L, exception.getRefillTimeSeconds());
        assertEquals("Reached notification limit type: " + type + " for user: " + userId, exception.getMessage());
    }
}
