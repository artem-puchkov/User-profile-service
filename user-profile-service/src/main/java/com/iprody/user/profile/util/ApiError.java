package com.iprody.user.profile.util;

import lombok.Value;
import java.util.List;

/**
 * The class is used to create an object that contains all info about an error.
 */
@Value
public class ApiError {
    /**
     * The error message.
     */
    private String message;

    /**
     * The list of error details.
     */
    private List<String> details;

    /**
     * HTTP Status (e. g. 400, 404, 500).
     */
    private int status;

}
