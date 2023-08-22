package com.iprody.user.profile.security;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public final class JwtProperties {

    /**
     * Base64-encoded Key for signing the generated token.
     */
    @Value("${jwt.secret}")
    private String secretKey;
    /**
     * JWT signature algorithm.
     */
    @Value("${jwt.alg}")
    private SignatureAlgorithm algorithm;
    /**
     * Access Token lifetime in seconds.
     */
    @Value("${jwt.expiration}")
    private long accessExpirationInSeconds;
    /**
     * Refresh Token lifetime in seconds.
     */
    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationInSeconds;
}
