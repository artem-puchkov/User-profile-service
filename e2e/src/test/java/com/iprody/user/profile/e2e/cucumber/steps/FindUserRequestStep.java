package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.HttpClientExceptionHandler;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import com.iprody.user.profile.e2e.generated.model.AuthenticationResponse;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindUserRequestStep {

    /**
     * Generated HTTP client for User-Profile service endpoints.
     */
    private final UserProfileControllerApi userProfileApi;

    /**
     * Step for storing userId in context for future use.
     *
     * @param userId of user we want to find
     */
    @When("a client wants to find a user with id {long}")
    public void aClientWantsToFindAUserWithId(long userId) {
        final var response = HttpClientExceptionHandler.sendRequest(
                () -> userProfileApi.findUserWithHttpInfo(userId));
        TestContextStorage.setResponseContext(response);
    }
}