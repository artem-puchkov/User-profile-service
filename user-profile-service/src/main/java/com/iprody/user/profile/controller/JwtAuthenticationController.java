package com.iprody.user.profile.controller;


import com.iprody.user.profile.dto.AuthenticationRequest;
import com.iprody.user.profile.dto.AuthenticationResponse;
import com.iprody.user.profile.dto.RefreshRequest;
import com.iprody.user.profile.service.JwtAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class JwtAuthenticationController {

    /**
     * Injection of service with business logic of authentication.
     */
    private JwtAuthenticationService jwtAuthenticationService;

    /**
     * @param request {@link AuthenticationRequest} email and password
     * @return Mono of {@link AuthenticationResponse} access and refresh tokens
     */
    @Operation(summary = "Authentication user")
    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthenticationResponse> auth(@RequestBody AuthenticationRequest request) {
        return jwtAuthenticationService.authenticate(request);
    }

    /**
     * @param refreshRequest {@link RefreshRequest} refresh token
     * @return Mono of {@link AuthenticationResponse} access and refresh tokens
     */
    @Operation(summary = "Refresh token")
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return jwtAuthenticationService.refresh(refreshRequest);
    }
}
