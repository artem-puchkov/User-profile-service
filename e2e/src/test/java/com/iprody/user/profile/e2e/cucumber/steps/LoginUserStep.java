package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.HttpClientExceptionHandler;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.generated.api.JwtAuthenticationControllerApi;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import com.iprody.user.profile.e2e.generated.model.AuthenticationRequest;
import com.iprody.user.profile.e2e.generated.model.AuthenticationResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginUserStep {

    @Autowired
    JwtAuthenticationControllerApi jwtController;

    @Autowired
    UserProfileControllerApi userProfileControllerApi;

    @When("a client wants login with email {string} and password {string}")
    public void aClientWantsLoginWithEmailAndPassword(String email, String password) {
        final AuthenticationRequest request = AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();

        final var response = HttpClientExceptionHandler.sendRequest(
                () -> jwtController.authWithHttpInfo(request));
        TestContextStorage.setResponseContext(response);
        TestContextStorage.clearAuthenticationContext();
    }

    @And("response body contains access token and refresh token")
    public void responseBodyContainsAccessTokenAndRefreshToken() {
        final AuthenticationResponse response = TestContextStorage.getResponseBody();
        Assertions.assertNotNull(response.getAccessToken());
        Assertions.assertNotNull(response.getRefreshToken());
    }

    @And("a client adds a Authorization header from response")
    public void aClientAddsAAuthorizationHeaderFromResponse() {
        final AuthenticationResponse response = TestContextStorage.getResponseBody();
        userProfileControllerApi.getApiClient().setBearerToken(response.getAccessToken());
    }

    @And("Clear Authorization header for other tests")
    public void clearAuthorizationHeaderForOtherTests() {
        TestContextStorage.clearAuthenticationContext();
    }
}
