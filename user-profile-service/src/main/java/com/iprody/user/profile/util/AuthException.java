package com.iprody.user.profile.util;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {
    /**
     * Constructor that calls super constructor of AuthenticationException.
     * @param message message about what exactly went wrong
     */
    public AuthException(final String message) {
        super(message);
    }
}
