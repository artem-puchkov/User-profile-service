package com.iprody.user.profile.config;

import com.iprody.user.profile.security.JwtAuthenticationManager;
import com.iprody.user.profile.security.BearerServerAuthenticationConverter;
import com.iprody.user.profile.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Slf4j
@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@FieldDefaults(makeFinal = true)
@AllArgsConstructor
public class WebSecurityConfig {

    /**
     * Public path for login.
     */
    private static final String LOGIN_URL = "/auth";
    /**
     * Public path for create Users.
     */
    private static final String REGISTRATION_URL = "/users";
    /**
     * Secured paths.
     */
    private static final String SECURED_URL = "/**";

    /**
     * Service for generating and validating tokens.
     */
    private JwtService jwtService;
    /**
     * ServerAuthenticationEntryPoint.
     * For exception handling
     */
    private CustomServerAuthEntryPoint customServerAuthEntryPoint;

    /**
     * @param http ServerHttpSecurity
     * @return SecurityWebFilterChain with security configuration
     */
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchangeSpec -> exchangeSpec
                        .pathMatchers(LOGIN_URL)
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, REGISTRATION_URL)
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(customServerAuthEntryPoint)
                )
                .addFilterAt(bearerAuthenticationFilter(new JwtAuthenticationManager(jwtService)),
                        SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }

    /**
     * @return BCrypt password encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @param manager custom impl ReactiveAuthenticationManager.
     * @return custom WebFilter with AuthManager and convertor request to Authentication
     */
    private AuthenticationWebFilter bearerAuthenticationFilter(JwtAuthenticationManager manager) {
        final AuthenticationWebFilter bearerAuthFilter = new AuthenticationWebFilter(manager);
        bearerAuthFilter.setServerAuthenticationConverter(new BearerServerAuthenticationConverter());
        bearerAuthFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(SECURED_URL));
        bearerAuthFilter.setAuthenticationFailureHandler(new CustomAuthFailureHandler(customServerAuthEntryPoint));
        return bearerAuthFilter;
    }
}
