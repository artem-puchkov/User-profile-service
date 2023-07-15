package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.HttpClientExceptionHandler;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.cucumber.UserProfileApiModelMapper;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import com.iprody.user.profile.e2e.generated.model.UserDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class UpdateUserStep {

    /**
     * Generated HTTP client for User-Profile service endpoints.
     */
    private final UserProfileControllerApi userProfileApi;

    /**
     * Step for storing userId in context for future use.
     *
     * @param userId of the user we want to update
     */
    @When("a client wants to update a user with id {long}")
    public void aClientWantsToUpdateAUserWithId(long userId) {
        TestContextStorage.setRequestContext(UserDto.builder()
                .id(userId)
                .build());
    }

    /**
     * Step for storing test data for updating user.
     *
     * @param dataTable with fields that we want to update
     */
    @And("wants to update a user to:")
    public void wantsToUpdateAUserTo(DataTable dataTable) {
        final var userId = TestContextStorage.getRequestUserDto().getId();
        final UserDto userDto = UserProfileApiModelMapper.toUserDto(dataTable, dto -> dto.setId(userId));
        final ResponseEntity<?> response = HttpClientExceptionHandler
                .sendRequest(() -> userProfileApi.updateUserWithHttpInfo(userId, userDto));
        TestContextStorage.setResponseContext(response);
    }
}
