package com.iprody.user.profile.security;

import com.iprody.user.profile.util.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    /**
     * Service for generating and validating tokens.
     */
    private final JwtService jwtService;

    /**
     * this class is a service class. all the logic in BearerServerAuthenticationConverter.
     * @param authentication accepted authentication object.
     * @return authentication in Mono.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .map(Authentication::getCredentials)
                .map(this::getStringCredentials)
                .doOnNext(jwtService::checkToken)
                .flatMap(token -> {
                    final String email = jwtService.extractUsername(token);
                    return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(email, token, null));
                });
    }

    /**
     * For safe cast Object Credentials to String.
     * @param credentials - object credentials
     * @return credentials cast to String or Exception
     */
    private String getStringCredentials(Object credentials) {
        final String result;
        try {
            result = (String) credentials;
        } catch (ClassCastException ex) {
            throw new AuthException("Invalid token");
        }
        return result;
    }
}
