package com.iprody.user.profile.e2e.cucumber.assertion;

import com.iprody.user.profile.e2e.cucumber.JsonSerializationHelper;
import com.iprody.user.profile.e2e.generated.model.ApiError;
import com.iprody.user.profile.e2e.generated.model.UserDetailsDto;
import com.iprody.user.profile.e2e.generated.model.UserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class FactoryDtoAssertHandler {

    JsonSerializationHelper jsonSerializationHelper;

    public DtoAssertHandler getInstance(Class<?> clazz) {
        if (clazz.equals(UserDto.class)) {
            return new UserDtoAssertion(jsonSerializationHelper);
        }
        if (clazz.equals(UserDetailsDto.class)) {
            return new UserDetailsDtoAssertion(jsonSerializationHelper);
        }
        if (clazz.equals(ApiError.class)) {
            return new ApiErrorAssertion(jsonSerializationHelper);
        }
        throw new IllegalArgumentException("An unprocessed data type was obtained: " + clazz.getName());
    }
}
