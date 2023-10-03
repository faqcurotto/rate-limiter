package com.modak.application.error.handler;

import com.modak.application.error.exception.RateLimitException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ExceptionHandler.class)
@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerTest {

    @Autowired
    ExceptionHandler handler;
    @Mock
    HttpServletRequest httpServletRequest;

    @Test
    void handleRateLimitException() {

        var exception = new RateLimitException(2L, "message1");

        var result = handler.handle(exception, httpServletRequest);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, result.getStatusCode());
        assertEquals("Reached the notification limit: message1", result.getBody());
        assertEquals(2L, Long.parseLong(result.getHeaders().get("REFILL_TIME_SECONDS").get(0)));
    }

    @Test
    void handleNoSuchElementException() {

        var exception = new NoSuchElementException("message1");

        var result = handler.handle(exception, httpServletRequest);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals("message1", result.getBody());
    }

    @Test
    void handleGenericException() {

        var exception = new IOException("message1");

        var result = handler.handle(exception, httpServletRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Internal Server Error: message1", result.getBody());
    }
}
