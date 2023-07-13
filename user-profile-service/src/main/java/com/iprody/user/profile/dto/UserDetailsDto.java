package com.iprody.user.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

/**
 * @param id          Can be null before saving entity.
 * @param telegramId  Cannot be null and must contain at least one non-whitespace character
 * @param mobilePhone Must match
 *                    <a href="https://www.twilio.com/docs/glossary/what-e164">E.164 format</a>
 * @param userId      Id of corresponding user
 */
@Builder
public record UserDetailsDto(
        Long id,
        @NotBlank(message = "Telegram field should not be empty")
        String telegramId,
        @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Mobile phone number is incorrect")
        String mobilePhone,
        Long userId
) {
}
