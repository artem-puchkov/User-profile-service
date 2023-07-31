package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.HttpClientExceptionHandler;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindUserDetailsStep {
    /**
     * Generated HTTP client for User-Profile service endpoints.
     */
    private final UserProfileControllerApi userProfileApi;

    /**
     * Step for storing userDetailsId in context for future use.
     *
     * @param userDetailsId of details we want to find
     */
    @When("a client wants to find a user details with id {long}")
    public void aClientWantsToFindAUserDetailsWithId(long userDetailsId) {
        final var response = HttpClientExceptionHandler.sendRequest(
                () -> userProfileApi.findUserDetailsWithHttpInfo(userDetailsId));
        TestContextStorage.setResponseContext(response);
    }
}
