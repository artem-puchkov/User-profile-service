package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.HttpClientExceptionHandler;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.cucumber.UserProfileApiModelMapper;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import com.iprody.user.profile.e2e.generated.model.AuthenticationResponse;
import com.iprody.user.profile.e2e.generated.model.CreateUserRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * StepDefinition that inits requests creation to service.
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CreateUserStep {

    /**
     * Generated HTTP client for User-Profile service endpoints.
     */
    UserProfileControllerApi userProfileApi;

    /**
     * Accept dataTable in feature file and update storage.
     *
     * @param dataTable - userDto parameters in feature file
     */
    @When("a client wants create user with parameters:")
    public void aClientWantCreateUserWithMandatoryParameters(DataTable dataTable) {
        final CreateUserRequest request = UserProfileApiModelMapper.toCreateUserRequest(dataTable);
        final var response = HttpClientExceptionHandler.sendRequest(
                () -> userProfileApi.createUserWithHttpInfo(request));
        TestContextStorage.setResponseContext(response);
    }
}
