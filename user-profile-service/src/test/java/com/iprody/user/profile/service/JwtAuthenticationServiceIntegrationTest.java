package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.AuthenticationRequest;
import com.iprody.user.profile.dto.AuthenticationResponse;
import com.iprody.user.profile.dto.RefreshRequest;
import com.iprody.user.profile.dto.TokenType;
import com.iprody.user.profile.security.JwtProperties;
import com.iprody.user.profile.util.AuthException;
import com.iprody.user.profile.util.BadCredentialsException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.test.StepVerifier;

import java.util.Date;

@IProdyIntegrationTest
@RequiredArgsConstructor
@ExtendWith(SoftAssertionsExtension.class)
public class JwtAuthenticationServiceIntegrationTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    private static final String VALID_PASSWORD = "123456";
    private static final String INCORRECT_PASSWORD = "00000000";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String PASSWORD_INCORRECT_MESSAGE = "Password incorrect";
    private static final String EXISTING_USER_EMAIL = "housegregory213@gmail.com";
    private static final String NON_EXISTING_USER_EMAIL = "nonexisting@gmail.com";
    private static final String INVALID_TOKEN = "Invalid token";
    private static final String TOKEN_REVOKED = "Token revoked";
    private static final String TOKEN_EXPIRED = "Token expired";
    private static final long MILL_TO_SEC_MULTIPLIER = 1000L;

    private final JwtAuthenticationService jwtAuthenticationService;
    private final TokenService tokenService;
    private final JwtProperties props;

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void whenLoginWithExistEmailAndPasswordSuccess(SoftAssertions softly) {
        final AuthenticationRequest request = AuthenticationRequest.builder()
                .email(EXISTING_USER_EMAIL)
                .password(VALID_PASSWORD)
                .build();

        StepVerifier.create(jwtAuthenticationService.authenticate(request))
                .assertNext(response -> {
                    softly.assertThat(response.getAccessToken()).isNotNull();
                    softly.assertThat(response.getRefreshToken()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    void whenLoginWithExistEmailAndInvalidPasswordFail(SoftAssertions softly) {
        final AuthenticationRequest request = AuthenticationRequest.builder()
                .email(EXISTING_USER_EMAIL)
                .password(INCORRECT_PASSWORD)
                .build();

        StepVerifier.create(jwtAuthenticationService.authenticate(request))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(BadCredentialsException.class)
                        .hasMessage(PASSWORD_INCORRECT_MESSAGE))
                .verify();
    }

    @Test
    void whenLoginWithNotExistedEmailAndValidPasswordFail(SoftAssertions softly) {
        final AuthenticationRequest request = AuthenticationRequest.builder()
                .email(NON_EXISTING_USER_EMAIL)
                .password(VALID_PASSWORD)
                .build();

        StepVerifier.create(jwtAuthenticationService.authenticate(request))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(BadCredentialsException.class)
                        .hasMessage(USER_NOT_FOUND))
                .verify();
    }

    @Test
    void whenRefreshWithValidRefreshTokenSucces(SoftAssertions softly) {
        final AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email(EXISTING_USER_EMAIL)
                .password(VALID_PASSWORD)
                .build();
        final AuthenticationResponse blockResponse = jwtAuthenticationService.authenticate(authRequest)
                        .block();
        final RefreshRequest refreshRequest = new RefreshRequest(blockResponse.getRefreshToken());

        StepVerifier.create(jwtAuthenticationService.refresh(refreshRequest))
                .assertNext(response -> {
                    softly.assertThat(response.getAccessToken()).isNotNull();
                    softly.assertThat(response.getRefreshToken()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    void whenRefreshWithNotRefreshTypeTokenFail(SoftAssertions softly) {
        final AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email(EXISTING_USER_EMAIL)
                .password(VALID_PASSWORD)
                .build();
        final AuthenticationResponse blockResponse = jwtAuthenticationService.authenticate(authRequest)
                .block();
        final RefreshRequest refreshRequest = new RefreshRequest(blockResponse.getAccessToken());

        StepVerifier.create(jwtAuthenticationService.refresh(refreshRequest))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(AuthException.class)
                        .hasMessage(INVALID_TOKEN))
                .verify();
    }

    @Test
    void whenRefreshWithNullRefreshTokenFail(SoftAssertions softly) {
        final RefreshRequest refreshRequest = new RefreshRequest();

        StepVerifier.create(jwtAuthenticationService.refresh(refreshRequest))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(AuthException.class)
                        .hasMessage(INVALID_TOKEN))
                .verify();
    }

    @Test
    void whenRefreshWithIncorrectRefreshTokenFail(SoftAssertions softly) {
        final RefreshRequest refreshRequest = new RefreshRequest("wsdfregfmop435435mk");

        StepVerifier.create(jwtAuthenticationService.refresh(refreshRequest))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(AuthException.class)
                        .hasMessage(INVALID_TOKEN))
                .verify();
    }

    @Test
    void whenRefreshWithExpiredRefreshTokenFail(SoftAssertions softly) {
        final String expiredRefreshToken = Jwts.builder()
                .setSubject(EXISTING_USER_EMAIL)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.getSecretKey())), props.getAlgorithm())
                .compact();

        tokenService.save(expiredRefreshToken, EXISTING_USER_EMAIL, TokenType.REFRESH);

        final RefreshRequest refreshRequest = new RefreshRequest(expiredRefreshToken);

        StepVerifier.create(jwtAuthenticationService.refresh(refreshRequest))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(AuthException.class)
                        .hasMessage(TOKEN_EXPIRED))
                .verify();
    }

    @Test
    void whenRefreshWithRefreshTokenIssuedOnNonExistingUserFail(SoftAssertions softly) {
        final String refreshToken = Jwts.builder()
                .setSubject(NON_EXISTING_USER_EMAIL)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + props.getRefreshExpirationInSeconds() * MILL_TO_SEC_MULTIPLIER))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.getSecretKey())), props.getAlgorithm())
                .compact();

        tokenService.save(refreshToken, EXISTING_USER_EMAIL, TokenType.REFRESH);

        final RefreshRequest refreshRequest = new RefreshRequest(refreshToken);

        StepVerifier.create(jwtAuthenticationService.refresh(refreshRequest))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(AuthException.class)
                        .hasMessage(USER_NOT_FOUND))
                .verify();
    }

    @Test
    void whenRefreshWithRevokedTokenFail(SoftAssertions softly) {
        final AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email(EXISTING_USER_EMAIL)
                .password(VALID_PASSWORD)
                .build();
        final AuthenticationResponse blockResponse = jwtAuthenticationService.authenticate(authRequest).block();
        final RefreshRequest refreshRequest = new RefreshRequest(blockResponse.getRefreshToken());

        jwtAuthenticationService.authenticate(authRequest).block(); // revoke old tokens for user

        StepVerifier.create(jwtAuthenticationService.refresh(refreshRequest))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(AuthException.class)
                        .hasMessage(TOKEN_REVOKED))
                .verify();
    }
}
