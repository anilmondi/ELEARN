package com.cts.elearn.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TraceIdFilter implements GlobalFilter {

    public static final String TRACE_ID_HEADER = "X-TRACE-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String traceId = UUID.randomUUID().toString();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(request -> request.header(TRACE_ID_HEADER, traceId))
                .build();

        MDC.put("traceId", traceId);

        return chain.filter(mutatedExchange)
                .doFinally(signalType -> MDC.clear());
    }
}
