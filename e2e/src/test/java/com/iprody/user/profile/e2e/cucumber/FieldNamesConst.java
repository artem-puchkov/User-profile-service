package com.iprody.user.profile.e2e.cucumber;

import lombok.experimental.UtilityClass;

/**
 * The class contains constants for correct parsing of rows in the table and json responses.
 * this is necessary for the correct mapping of fields in the table and json responses.
 */
@UtilityClass
public class FieldNamesConst {
    /**
     * Separator for details values in the details column feature file.
     * it is used to convert a string into a collection
     */
    String ERROR_DETAILS_SEPARATOR = ";";

    /**
     * Name of the field containing the details in json response and feature file.
     */
    String ERROR_DETAILS_NAME = "details";

    /**
     * Name of the field containing the user details in json response.
     */
    String USER_DETAILS_JSON_NAME = "userDetailsDto";

    /**
     * Prefix for separating user fields and user details fields in the table feature file.
     * ignored case
     */
    String USER_DETAILS_FIELD_NAME = "userdetails";

    /**
     * Separator between the name of user details field and prefix in the table feature file.
     * put after the prefix
     */
    String USER_DETAILS_FIELD_SEPARATOR = ".";
}
