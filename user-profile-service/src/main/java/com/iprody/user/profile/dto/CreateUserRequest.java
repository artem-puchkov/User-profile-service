package com.iprody.user.profile.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * @param id             Can be null before saving entity
 * @param firstName      Cannot be null and must contain at least one non-whitespace character.
 *                       Max length 30 characters.
 * @param lastName       Cannot be null and must contain at least one non-whitespace character.
 *                       Max length 30 characters.
 * @param email          Cannot be null, empty and must follow email pattern.
 * @param password       Cannot be empty, null, must contain at least:
 *                       1 lowercase letter, 1 uppercase letter, 1 digit, 1 special symbol - @#$%^&+=
 *                       And must be at least 8 characters long.
 * @param userDetailsDto Validated field {@link UserDetailsDto}
 */
@Builder
public record CreateUserRequest(
        Long id,
        @NotBlank(message = "First name should not be empty")
        @Size(max = 30, message = "First name should not be longer than 30")
        String firstName,
        @NotBlank(message = "Last name should not be empty")
        @Size(max = 30, message = "Last name should not be longer than 30")
        String lastName,
        @NotNull(message = "Email field should not be empty")
        @Pattern(regexp = "^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$",
                message = "Email field should be like: user@domain.com")
        String email,
        @NotNull(message = "Password should not be empty")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must be at least 8 characters long and must contain at least one"
                        + " lowercase and uppercase letter, one digit and one special symbol")
        String password,
        @Valid
        @NotNull
        UserDetailsDto userDetailsDto
) {
}
