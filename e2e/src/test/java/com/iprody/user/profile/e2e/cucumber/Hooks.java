package com.iprody.user.profile.e2e.cucumber;

import com.iprody.user.profile.e2e.generated.api.ActuatorApi;
import com.iprody.user.profile.e2e.generated.api.JwtAuthenticationControllerApi;
import com.iprody.user.profile.e2e.generated.api.UserProfileControllerApi;
import com.iprody.user.profile.e2e.generated.model.AuthenticationRequest;
import com.iprody.user.profile.e2e.generated.model.AuthenticationResponse;
import io.cucumber.java.Before;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Hooks {

    JwtAuthenticationControllerApi jwtApi;
    ActuatorApi actuatorApi;
    UserProfileControllerApi userProfileApi;

    @Before
    public void givenAuthenticationToken() {
        if(TestContextStorage.getAuthenticationContext().get() != null) {
            return;
        }
        final AuthenticationRequest request = AuthenticationRequest.builder()
                .email("jwt@gmail.com")
                .password("123456")
                .build();
        final ResponseEntity<AuthenticationResponse> response = jwtApi.authWithHttpInfo(request);
        actuatorApi.getApiClient().setBearerToken(response.getBody().getAccessToken());
        userProfileApi.getApiClient().setBearerToken(response.getBody().getAccessToken());
        TestContextStorage.setAuthenticationContext(response);
    }
}
