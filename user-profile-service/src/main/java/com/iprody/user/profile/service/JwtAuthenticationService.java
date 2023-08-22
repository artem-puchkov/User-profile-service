package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.AuthenticationRequest;
import com.iprody.user.profile.dto.AuthenticationResponse;
import com.iprody.user.profile.dto.RefreshRequest;
import com.iprody.user.profile.dto.TokenType;
import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.persistence.UserRepository;
import com.iprody.user.profile.security.JwtService;
import com.iprody.user.profile.util.AuthException;
import com.iprody.user.profile.util.BadCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    /**
     * Error message if user not found.
     */
    private static final String USER_NOT_FOUND = "User not found";
    /**
     * Error message if password incorrect.
     */
    private static final String PASSWORD_INCORRECT = "Password incorrect";
    /**
     * Error message if token not found, has incorrect type or not readable.
     */
    private static final String INVALID_TOKEN = "Invalid token";
    /**
     * UserRepository for extracting the user's entity.
     */
    private final UserRepository userRepository;
    /**
     * Service for generating and validating tokens.
     */
    private final JwtService jwtService;
    /**
     * TokenService for saving and updating tokens in database.
     */
    private final TokenService tokenService;
    /**
     * Password encoder for decode users password in db.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Authentication users.
     * @param request - request with email and password
     * @return response with access and refresh tokens
     */
    public Mono<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return findUserByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.error(new BadCredentialsException(PASSWORD_INCORRECT)))
                .map(user -> {
                    final String accessToken = jwtService.generateAccessToken(user.getEmail());
                    final String refreshToken = jwtService.generateRefreshToken(user.getEmail());
                    tokenService.revokeAllUserTokens(request.getEmail());
                    tokenService.save(accessToken, request.getEmail(), TokenType.ACCESS);
                    tokenService.save(refreshToken, request.getEmail(), TokenType.REFRESH);
                    return AuthenticationResponse
                            .builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                });


    }

    /**
     * Refresh tokens.
     * @param refreshRequest - request with refresh token
     * @return response with access and refresh tokens
     */
    public Mono<AuthenticationResponse> refresh(RefreshRequest refreshRequest) {
        final String refreshToken = refreshRequest.getRefreshToken();
        if (refreshToken == null || !tokenService.isRefreshToken(refreshToken)) {
            return Mono.error(new AuthException(INVALID_TOKEN));
        }
        try {
            jwtService.checkToken(refreshToken);
        } catch (AuthException ex) {
            return Mono.error(ex);
        }
        final String email = jwtService.extractUsername(refreshToken);
        final String accessToken = jwtService.generateAccessToken(email);
        final String newRefreshToken = jwtService.generateRefreshToken(email);
        tokenService.revokeAllUserTokens(email);
        tokenService.save(accessToken, email, TokenType.ACCESS);
        tokenService.save(newRefreshToken, email, TokenType.REFRESH);

        return Mono.just(AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build());
    }

    /**
     * Retrieves user from the database by email.
     * @param email - users email
     * @return User or Exception if user not found
     */
    private Mono<User> findUserByEmail(String email) {
        return Mono.fromCallable(() -> userRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .switchIfEmpty(Mono.error(new BadCredentialsException(USER_NOT_FOUND)));
    }
}
