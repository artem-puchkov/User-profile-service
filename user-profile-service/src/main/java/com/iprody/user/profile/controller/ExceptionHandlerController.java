package com.iprody.user.profile.controller;

import com.iprody.user.profile.util.ApiError;
import com.iprody.user.profile.util.ResourceNotFoundException;
import com.iprody.user.profile.util.ResourceProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

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
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(ResourceNotFoundException e) {
        final ApiError response = new ApiError(
                RESOURCE_NOT_FOUND, Collections.singletonList(e.getMessage()),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Methode that catch and handles exceptions that may have occurred while processing some resource. After that
     * appends a message about error, details and status code.
     *
     * @param e expects an ResourceProcessingException error to occur.
     * @return ResponseEntity object that contains error message, details,
     * status code and HttpStatus - INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(ResourceProcessingException.class)
    public ResponseEntity<ApiError> handleResourceProcessingException(ResourceProcessingException e) {
        final ApiError response = new ApiError(
                RESOURCE_PROCESSING_ERROR, Collections.singletonList(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Methode that catch and handles exceptions that may have occurred during a validation error. After that appends
     * a message about error, details and status code.
     *
     * @param ex the exception to handle
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param exchange the current request and response
     * @return ResponseEntity object that contains error message, details,
     * status code and HttpStatus - INTERNAL_SERVER_ERROR.
     *
     */
    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          ServerWebExchange exchange) {
        final List<String> details = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        final ApiError response = new ApiError(
                VALIDATION_ERROR, details, HttpStatus.BAD_REQUEST.value());
        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }

}
