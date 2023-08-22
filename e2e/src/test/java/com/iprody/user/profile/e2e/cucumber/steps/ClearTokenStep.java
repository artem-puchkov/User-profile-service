package com.iprody.user.profile.e2e.cucumber.steps;

import com.iprody.user.profile.e2e.cucumber.TestContextStorage;
import com.iprody.user.profile.e2e.generated.api.ActuatorApi;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import io.cucumber.java.en.Given;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClearTokenStep {

    UserProfileControllerApi userProfileControllerApi;
    ActuatorApi actuatorApi;

    @Given("client does not have a token")
    public void clientDoesNotHaveAToken() {
        TestContextStorage.clearAuthenticationContext();
        userProfileControllerApi.getApiClient().setBearerToken(null);
        actuatorApi.getApiClient().setBearerToken(null);
    }
}
