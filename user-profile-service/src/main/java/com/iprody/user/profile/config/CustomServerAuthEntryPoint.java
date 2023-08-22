package com.iprody.user.profile.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.user.profile.util.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Component
public class CustomServerAuthEntryPoint implements ServerAuthenticationEntryPoint {
    /**
     * Mapper for converting an ApiError object to a json string.
     */
    private ObjectMapper objectMapper;

    /**
     * Initiates the authentication flow.
     * @param exchange ServerWebExchange
     * @param ex handling Exception
     * @return Mono to indicate when the request for authentication is complete
     */
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        final ApiError apiError = new ApiError("UNAUTHORIZED", Collections.singletonList(ex.getMessage()),
                HttpStatus.UNAUTHORIZED.value());
        final byte[] responseBody = convertToJsonByteArray(apiError);
        final DataBuffer buffer = response.bufferFactory().wrap(responseBody);
        return response.writeWith(Mono.just(buffer));
    }
    /**
     * Convert Java object to Json byte array.
     * @param object - pojo
     * @return Json byte array
     */
    private byte[] convertToJsonByteArray(Object object) {
        final byte[] result;
        try {
            result = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException ex) {
            throw new UnsupportedOperationException(ex.getMessage());
        }
        return result;
    }
}
