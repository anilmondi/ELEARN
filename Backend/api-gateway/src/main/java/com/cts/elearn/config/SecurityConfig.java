package com.cts.elearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        // IMPORTANT: do NOT use authenticated() here
        http.authorizeExchange(exchange -> exchange
                .anyExchange().permitAll()
        );

        return http.build();
    }
}

