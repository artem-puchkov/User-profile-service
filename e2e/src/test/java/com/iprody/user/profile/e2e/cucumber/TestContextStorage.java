package com.iprody.user.profile.e2e.cucumber;

import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

/**
 * Service class for saving step results and transmitting data.
 */
@UtilityClass
public class TestContextStorage {

    /**
     * Thread safe storage for response.
     */
    private ThreadLocal<ResponseEntity<Object>> responseContext = new ThreadLocal<>();

    /**
     * Return Reasponse stored in the same thread.
     * @return - ResponseEntity with body(UserDto or ApiError) and Http status
     */
    public ThreadLocal<ResponseEntity<Object>> getResponseContext() {
        return responseContext;
    }

    /**
     * Save Reasponse in trhed safe storage.
     * @param context - ResponseEntity with body (userDto or ApiError)
     */
    public void setResponseContext(ResponseEntity<?> context) {
            TestContextStorage.responseContext.set( (ResponseEntity<Object>) context);
    }

    /**
     * Return http status of response stored in the same thread.
     * @return - Http status value
     */
    public int getStatus() {
        return getResponse().getStatusCode().value();
    }

    /**
     * Return responseEntity stored in the same thread.
     * @return - T response
     */
    public ResponseEntity<Object> getResponse() {
        return getResponseContext().get();
    }

    /**
     * Return generic responseBody stored in the same thread.
     * @param <T> - type to cast responseBody
     * @return - T responseBody
     */
    public <T> T getResponseBody() {
        return (T) getResponse().getBody();
    }

    public Class<?> getResponseBodyType() {
        return getResponseBody().getClass();
    }
}
