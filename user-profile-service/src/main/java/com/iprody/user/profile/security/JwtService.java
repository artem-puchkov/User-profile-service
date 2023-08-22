package com.iprody.user.profile.security;

import com.iprody.user.profile.persistence.UserRepository;
import com.iprody.user.profile.service.TokenService;
import com.iprody.user.profile.util.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;


@Component
@Slf4j
public class JwtService {

    /**
     * Error message if user not found.
     */
    private static final String USER_NOT_FOUND = "User not found";
    /**
     * Error message if token not found, has incorrect type or not readable.
     */
    private static final String INVALID_TOKEN = "Invalid token";
    /**
     * Error message if token expired.
     */
    private static final String TOKEN_EXPIRED = "Token expired";
    /**
     * Error message if token revoked.
     */
    private static final String TOKEN_REVOKED = "Token revoked";
    /**
     * to convert milliseconds to seconds.
     */
    private static final long MILL_TO_SEC_MULTIPLIER = 1000L;
    /**
     * UserRepository for extracting the user's entity.
     */
    private final UserRepository userRepository;
    /**
     * TokenService for saving and updating tokens in database.
     */
    private final TokenService tokenService;
    /**
     * application properties for JWT generating.
     */
    private final JwtProperties props;
    /**
     * Constructor with all parameters.
     * @param userRepository -
     * @param tokenService -
     * @param props - application properties for JWT
     */
    public JwtService(
            final UserRepository userRepository,
            final TokenService tokenService,
            final JwtProperties props) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.props = props;
    }

    /**
     * Access Token generation method.
     * @param email - users email. will be added to subject field
     * @return Access token signed key with expiration time = jwtExpirationInSeconds
     */
    public String generateAccessToken(String email) {
        return generateToken(email, props.getAccessExpirationInSeconds());
    }

    /**
     * Refresh Token generation method.
     * @param email - users email. will be added to subject field
     * @return Access token signed key with expiration time = refreshExpirationInSeconds
     */
    public String generateRefreshToken(String email) {
        return generateToken(email, props.getRefreshExpirationInSeconds());
    }

    /**
     * Checks the validity of the token.
     * @param token - access or refresh token
     * throws respective Exception if token invalid
     */
    public void checkToken(String token) {
        if (isNotValidToken(token)) {
            throw new AuthException(INVALID_TOKEN);
        }
        if (!tokenService.isNotRevokedToken(token)) {
            throw new AuthException(TOKEN_REVOKED);
        }
        final String username = extractUsername(token);
        if (username == null) {
            throw new AuthException(INVALID_TOKEN);
        }
        if (!userRepository.existsByEmail(username)) {
            throw new AuthException(USER_NOT_FOUND);
        }
    }

    /**
     * Extracts the user email from the token.
     * @param token - access or refresh token
     * @return user email or null if token unreadable
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Generic method for generate tokens.
     * @param email - data for subject claim
     * @param expirationTime - expiration time in second
     * @return generated jwt token
     */
    private String generateToken(String email, long expirationTime) {
        return Jwts.builder()
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getExpirationDate(expirationTime))
                .signWith(getSignInKey(), props.getAlgorithm())
                .compact();
    }

    /**
     * Generates a token signing key.
     * @return Key for use with HMAC-SHA algorithms based on the sekretKey byte array.
     */
    private Key getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(props.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts all Claims from the token.
     * @param token - access or refresh token
     * @return Claims or null if token unreadable
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Checks the token lifetime and readable.
     * @param token - access or refresh token
     * @return true if token not expired
     */
    private boolean isNotValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException ex) {
            log.warn("Expired JWT token; {}", token);
            throw new AuthException(TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
            log.warn("Failed read JWT token: {}, error: {}", token, ex.getMessage());
            return true;
        }
    }

    /**
     * Expiration Date generate.
     * @param time in seconds
     * @return Date = now + time in seconds
     */
    private Date getExpirationDate(long time) {
        return new Date(System.currentTimeMillis() + time * MILL_TO_SEC_MULTIPLIER);
    }
}
