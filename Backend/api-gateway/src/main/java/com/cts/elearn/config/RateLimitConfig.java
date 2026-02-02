package com.cts.elearn.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class RateLimitConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange ->
                Mono.justOrEmpty(
                        exchange.getRequest().getHeaders().getFirst("X-USER-ID")
                ).switchIfEmpty(
                        Mono.just(
                                exchange.getRequest()
                                        .getRemoteAddress()
                                        .getHostName()
                        )
                );
    }
}
