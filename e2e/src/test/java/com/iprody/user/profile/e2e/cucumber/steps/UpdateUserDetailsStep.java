package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.HttpClientExceptionHandler;
import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.cucumber.UserProfileApiModelMapper;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import com.iprody.user.profile.e2e.generated.model.UserDetailsDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class UpdateUserDetailsStep {

    /**
     * Generated HTTP client for User-Profile service endpoints.
     */
    private final UserProfileControllerApi userProfileApi;

    @When("a client wants to update user details with user ID {long}")
    public void aClientWantsToUpdateUserDetailsWithUserId(long userId) {
        TestContextStorage.setRequestContext(UserDetailsDto.builder()
                .userId(userId)
                .build());
    }

    @When("wants to update user details to:")
    public void wantsToUpdateUserDetailsTo(DataTable dataTable) {
        final var userId = TestContextStorage.getRequestUserDetailsDto().getUserId();
        final var userDetailsDto = UserProfileApiModelMapper.toUserDetailsDto(
                dataTable,
                dto -> dto.setUserId(userId));
        final ResponseEntity<?> response = HttpClientExceptionHandler
                .sendRequest(() -> userProfileApi.updateUserDetailsWithHttpInfo(userId, userDetailsDto));
        TestContextStorage.setResponseContext(response);
    }
}
