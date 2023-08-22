package com.iprody.user.profile.util;

/**
 * The class is used to create an exception object that is thrown when an unsuccessful attempt to log in to the system.
 * Status code - 400.
 */
public class BadCredentialsException extends RuntimeException {
    /**
     * Constructor that calls super constructor of RuntimeException.
     * @param message message about what exactly went wrong
     */
    public BadCredentialsException(final String message) {
        super(message);
    }

}
