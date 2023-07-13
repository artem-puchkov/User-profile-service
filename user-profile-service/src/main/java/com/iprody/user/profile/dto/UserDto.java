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
 * @param userDetailsDto Validated field {@link UserDetailsDto}
 */
@Builder
public record UserDto(
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
        @Valid
        @NotNull
        UserDetailsDto userDetailsDto
) {
}
