package com.iprody.user.profile.controller;

import com.iprody.user.profile.util.ApiError;
import com.iprody.user.profile.util.ResourceNotFoundException;
import com.iprody.user.profile.util.ResourceProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Error message if the resource was not found.
     */
    private static final String RESOURCE_NOT_FOUND = "Resource was not found";
    /**
     * Error message if an error occurred while processing some resource.
     */
    private static final String RESOURCE_PROCESSING_ERROR = "Error occurred during processing the resource";
    /**
     * Error message if some validation errors occurred.
     */
    private static final String VALIDATION_ERROR = "Request validation error occurred";

    /**
     * Methode that catch and handles exceptions when user specified an invalid id when updating some user's info
     * and other such errors. After that appends a message about error, details and status code.
     *
     * @param e expects an ResourceNotFoundException error to occur.
     * @return ResponseEntity object that contains error message, details, status code and HttpStatus - NOT_FOUND.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(ResourceNotFoundException e) {
        final ApiError response = new ApiError(
                RESOURCE_NOT_FOUND, Collections.singletonList(e.getMessage()),
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Methode that catch and handles exceptions that may have occurred while processing some resource. After that
     * appends a message about error, details and status code.
     *
     * @param e expects an ResourceProcessingException error to occur.
     * @return ResponseEntity object that contains error message, details,
     * status code and HttpStatus - INTERNAL_SERVER_ERROR.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceProcessingException.class)
    public ResponseEntity<ApiError> handleResourceProcessingException(ResourceProcessingException e) {
        final ApiError response = new ApiError(
                RESOURCE_PROCESSING_ERROR, Collections.singletonList(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.internalServerError().body(response);
    }

    /**
     * Methode that catch and handles exceptions that may have occurred during a validation error. After that appends
     * a message about error, details and status code.
     *
     * @param ex the exception to handle
     * @return ResponseEntity object that contains error message, details,
     * status code and HttpStatus - INTERNAL_SERVER_ERROR.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiError> handleWebExchangeBindException(WebExchangeBindException ex) {
        final List<String> details = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        final ApiError response = new ApiError(
                VALIDATION_ERROR, details, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(response);
    }
}
