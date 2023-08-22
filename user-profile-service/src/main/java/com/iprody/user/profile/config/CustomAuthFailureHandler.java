package com.iprody.user.profile.config;

import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;

public class CustomAuthFailureHandler extends ServerAuthenticationEntryPointFailureHandler {

    /**
     * required constructor ServerAuthenticationEntryPointFailureHandler.
     * @param authenticationEntryPoint - implementation ServerAuthenticationEntryPoint
     */
    public CustomAuthFailureHandler(final ServerAuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }
}
