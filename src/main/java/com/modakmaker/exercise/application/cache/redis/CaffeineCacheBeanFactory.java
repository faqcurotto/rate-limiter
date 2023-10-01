package com.modakmaker.exercise.application.cache.redis;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CaffeineCacheBeanFactory {

    @Value("${cache.caffeine.proxy.keepAfterRefillDurationSeconds}")
    private Long keepAfterRefillDuration;
    @Bean
    ProxyManager<String> proxyManager() {
        return new CaffeineProxyManager<>(Caffeine.newBuilder().recordStats(),
                Duration.ofSeconds(keepAfterRefillDuration));
    }
}
