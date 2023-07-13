package com.iprody.user.profile.util;

/**
 * The class is used to create an exception object that is thrown when a requested resource is not found.
 * Status code - 404.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructor that calls super constructor of RuntimeException.
     * @param message message about what exactly went wrong
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }

}
