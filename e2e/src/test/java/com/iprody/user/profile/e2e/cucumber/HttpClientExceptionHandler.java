package com.iprody.user.profile.e2e.cucumber;

import com.iprody.user.profile.e2e.generated.model.ApiError;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import java.util.function.Supplier;

/**
 * Service class for cath Http client exceptions and create error response.
 */
@UtilityClass
public class HttpClientExceptionHandler {

    /**
     * Send request to service and create response.
     * when response with status 40X or 50X then catch client Exception
     * @param request - accepts lamda function for call Api.
     *                Accepts Supplier so that the service api call is executed in this method
     * @return ResponseEntity with body (UserDto or ApiError*)
     * *!!! ApiError is not ready yet , temporarily String
     */
    public ResponseEntity<?> sendRequest(Supplier<ResponseEntity<?>> request) {
        try {
            return request.get();
        } catch (RestClientResponseException ex) {
            final var apiError = ex.getResponseBodyAs(ApiError.class);
            return ResponseEntity.status(ex.getStatusCode()).body(apiError);
        }
    }
}
