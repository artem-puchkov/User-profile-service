package com.iprody.user.profile.util;

/**
 * The class is used to create an exception object that is thrown if there are problems in processing the resource.
 * Status code - 500.
 */
public class ResourceProcessingException extends RuntimeException {
    /**
     * Constructor that calls super constructor of RuntimeException.
     * @param message message about what exactly went wrong
     */
    public ResourceProcessingException(final String message) {
        super(message);
    }

}
