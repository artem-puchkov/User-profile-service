package com.iprody.user.profile.e2e.cucumber;

import com.iprody.user.profile.e2e.generated.model.UserDto;
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
    private final ThreadLocal<ResponseEntity<Object>> responseContext = new ThreadLocal<>();

    /**
     * Thread safe storage for request.
     */
    private final ThreadLocal<Object> requestContext = new ThreadLocal<>();

    /**
     * Return Response stored in the same thread.
     *
     * @return - ResponseEntity with body(UserDto or ApiError) and Http status
     */
    public ThreadLocal<ResponseEntity<Object>> getResponseContext() {
        return responseContext;
    }

    /**
     * Save Response in thread safe storage.
     *
     * @param context - ResponseEntity with body (userDto or ApiError)
     */
    public void setResponseContext(ResponseEntity<?> context) {
        TestContextStorage.responseContext.set((ResponseEntity<Object>) context);
    }

    /**
     * Return http status of response stored in the same thread.
     *
     * @return - Http status value
     */
    public int getStatus() {
        return getResponse().getStatusCode().value();
    }

    /**
     * Return responseEntity stored in the same thread.
     *
     * @return - response
     */
    public ResponseEntity<Object> getResponse() {
        return getResponseContext().get();
    }

    /**
     * Return generic responseBody stored in the same thread.
     *
     * @param <T> - type to cast responseBody
     * @return - T responseBody
     */
    public <T> T getResponseBody() {
        return (T) getResponse().getBody();
    }

    /**
     * @return Class of ResponseBody
     */
    public Class<?> getResponseBodyType() {
        return getResponseBody().getClass();
    }

    /**
     * @return context for request
     */
    public ThreadLocal<Object> getRequestContext() {
        return requestContext;
    }

    /**
     * Initializing request context with object inside.
     *
     * @param request object that we are storing in context for use between steps
     */
    public void setRequestContext(Object request) {
        requestContext.set(request);
    }

    /**
     * @return object stored in the request context
     */
    public Object getRequestBody() {
        return getRequestContext().get();
    }

    /**
     * @return UserDto stored in context
     */
    public UserDto getRequestUserDto() {
        return (UserDto) getRequestBody();
    }
}
