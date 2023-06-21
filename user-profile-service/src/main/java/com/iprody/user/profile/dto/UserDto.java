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
        @NotBlank
        @Size(max = 30)
        String firstName,
        @NotBlank
        @Size(max = 30)
        String lastName,
        @NotNull
        @Pattern(regexp = "^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$")
        String email,
        @Valid
        @NotNull
        UserDetailsDto userDetailsDto
) {
}
