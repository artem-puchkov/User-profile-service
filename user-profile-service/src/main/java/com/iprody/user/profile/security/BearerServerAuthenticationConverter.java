package com.iprody.user.profile.security;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class BearerServerAuthenticationConverter implements ServerAuthenticationConverter {

    /**
     * Authorization header prefix.
     */
    private static final String BEARER_PREFIX = "Bearer ";
    /**
     * Function for extract header value without prefix and wrapping in Mono.
     */
    private static final Function<String, Mono<String>> GET_BEARER_VALUE = headerValue ->
            Mono.justOrEmpty(headerValue.substring(BEARER_PREFIX.length()));

    /**
     * Extract authentication object from request and convert to Authentication.
     * @param exchange - ServerWebExchange
     * @return authentication object if credentials is correct
     */
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return extractHeader(exchange)
                .flatMap(GET_BEARER_VALUE)
                .flatMap(token -> {
                    return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(token, token, null));
                });
    }

    /**
     * Extract token value from ServerWebExchange.
     * @param exchange - ServerWebExchange
     * @return token value in Mono
     */
    private Mono<String> extractHeader(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
        .getHeaders()
        .getFirst(HttpHeaders.AUTHORIZATION))
        .filter(token -> token.length() > BEARER_PREFIX.length());
    }
}
