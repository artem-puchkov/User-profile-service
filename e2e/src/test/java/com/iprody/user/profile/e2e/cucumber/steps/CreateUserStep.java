package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.HttpClientExceptionHandler;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.cucumber.UserProfileApiModelMapper;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import com.iprody.user.profile.e2e.generated.model.UserDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;


/**
 * Stepdefinition that init create requests to service.
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
     * @param dataTable - userDto parameters in feature file
     */
    @When("a client wants create user with parameters:")
    public void aClientWantCreateUserWithMandatoryParameters(DataTable dataTable) {
        final UserDto userDto = UserProfileApiModelMapper.toUserDto(dataTable);
        final var response = HttpClientExceptionHandler.sendRequest(
                () -> userProfileApi.createUserWithHttpInfo(userDto));
        TestContextStorage.setResponseContext(response);
    }
}