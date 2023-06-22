package com.iprody.user.profile.controller;

import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.dto.UserDto;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @author Mikhail Sheludyakov
 */

final class DummyUserFactory {

    /**
     * User valid email.
     */
    private static final String EMAIL = "email@iprody.com";
    /**
     * User valid firstname.
     */
    private static final String FIRSTNAME = "firstname";
    /**
     * User valid lastname.
     */
    private static final String LASTNAME = "lastname";
    /**
     * UserDetails valid telegramId.
     */
    private static final String TELEGRAM_ID = "@telegram";
    /**
     * UserDetails valid mobile phone.
     */
    private static final String MOBILE_PHONE = "+1234567890";
    /**
     * User valid id.
     */
    private static final long USER_ID = 1L;
    /**
     * Blank string.
     */
    private static final String BLANK_STRING = " ";
    /**
     * Empty string.
     */
    private static final String EMPTY_STRING = "";
    /**
     * String longer than 30 characters.
     */
    private static final String STRING_LONGER_THAN_30 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

    private DummyUserFactory() {
    }

    /**
     * @return Valid UserDto
     */
    public static UserDto getValidUserDto() {

        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return Valid UserDetailDto
     */
    public static UserDetailsDto getValidUserDetailsDto() {
        return UserDetailsDto.builder()
                .telegramId(TELEGRAM_ID)
                .mobilePhone(MOBILE_PHONE)
                .build();
    }

    /**
     * @return Valid UserDto with Id
     */
    public static UserDto getValidUserDtoWithId() {
        return UserDto.builder()
                .id(USER_ID)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDtoWithId())
                .build();
    }

    /**
     * @return Valid UserDetailDto with Id
     */
    public static UserDetailsDto getValidUserDetailsDtoWithId() {
        return UserDetailsDto.builder()
                .id(USER_ID)
                .telegramId(TELEGRAM_ID)
                .mobilePhone(MOBILE_PHONE)
                .build();
    }

    /**
     * @return UserDto with null firstname
     */
    public static UserDto getUserDtoWithFirstNameNull() {
        return UserDto.builder()
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with empty firstname
     */
    public static UserDto getUserDtoWithFirstNameEmpty() {
        return UserDto.builder()
                .firstName(EMPTY_STRING)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with blank firstname
     */
    public static UserDto getUserDtoWithFirstNameBlank() {
        return UserDto.builder()
                .firstName(BLANK_STRING)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with firstname length > 30
     */
    public static UserDto getUserDtoWithFirstNameLengthGreaterThan30() {
        return UserDto.builder()
                .firstName(STRING_LONGER_THAN_30)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with null lastname
     */
    public static UserDto getUserDtoWithLastNameNull() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with empty lastname
     */
    public static UserDto getUserDtoWithLastNameEmpty() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(EMPTY_STRING)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with blank lastname
     */
    public static UserDto getUserDtoWithLastNameBlank() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(BLANK_STRING)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with lastname length > 30
     */
    public static UserDto getUserDtoWithLastNameLengthGreaterThan30() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(STRING_LONGER_THAN_30)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with null email
     */
    public static UserDto getUserDtoWithEmailNull() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with empty email
     */
    public static UserDto getUserDtoWithEmailEmpty() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMPTY_STRING)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with blank email
     */
    public static UserDto getUserDtoWithEmailBlank() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(BLANK_STRING)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with invalid email
     */
    public static UserDto getUserDtoWithEmailInvalid() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email("email.ru")
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDetailsDto with null telegramId
     */
    public static UserDetailsDto getUserDetailsDtoWithTelegramIdNull() {
        return UserDetailsDto.builder().build();
    }

    /**
     * @return UserDetailsDto with invalid telegramId
     */
    public static UserDetailsDto getUserDetailsDtoWithTelegramIdBlank() {
        return UserDetailsDto.builder()
                .telegramId(BLANK_STRING)
                .build();
    }

    /**
     * @return UserDetailsDto with invalid mobile phone
     */
    public static UserDetailsDto getUserDetailsDtoWithMobilePhoneNotMatchesPattern() {
        return UserDetailsDto.builder()
                .telegramId(TELEGRAM_ID)
                .mobilePhone("123")
                .build();
    }

    /**
     * @return UserDto with null telegramId
     */
    public static UserDto getUserDtoWithTelegramIdNull() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getUserDetailsDtoWithTelegramIdNull())
                .build();
    }

    /**
     * @return UserDto with invalid telegramId
     */
    public static UserDto getUserDtoWithTelegramIdBlank() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getUserDetailsDtoWithTelegramIdBlank())
                .build();
    }

    /**
     * @return UserDto with invalid mobile phone
     */
    public static UserDto getUserDtoWithMobilePhoneNotMatchesPattern() {
        return UserDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getUserDetailsDtoWithMobilePhoneNotMatchesPattern())
                .build();
    }

    /**
     * @return Stream of Arguments with invalid UserDto's
     */
    public static Stream<Arguments> getInvalidUserDtoArguments() {
        return Stream.of(
                Arguments.of(getUserDtoWithFirstNameNull()),
                Arguments.of(getUserDtoWithFirstNameEmpty()),
                Arguments.of(getUserDtoWithFirstNameBlank()),
                Arguments.of(getUserDtoWithFirstNameLengthGreaterThan30()),
                Arguments.of(getUserDtoWithLastNameNull()),
                Arguments.of(getUserDtoWithLastNameEmpty()),
                Arguments.of(getUserDtoWithLastNameBlank()),
                Arguments.of(getUserDtoWithLastNameLengthGreaterThan30()),
                Arguments.of(getUserDtoWithEmailNull()),
                Arguments.of(getUserDtoWithEmailEmpty()),
                Arguments.of(getUserDtoWithEmailBlank()),
                Arguments.of(getUserDtoWithEmailInvalid()),
                Arguments.of(getUserDtoWithTelegramIdNull()),
                Arguments.of(getUserDtoWithTelegramIdBlank()),
                Arguments.of(getUserDtoWithMobilePhoneNotMatchesPattern())
        );
    }

    /**
     * @return Stream of Arguments with invalid UserDetailsDto's
     */
    public static Stream<Arguments> getInvalidUserDetailsDtoArguments() {
        return Stream.of(
                Arguments.of(getUserDetailsDtoWithMobilePhoneNotMatchesPattern()),
                Arguments.of(getUserDetailsDtoWithTelegramIdNull()),
                Arguments.of(getUserDetailsDtoWithTelegramIdBlank())
        );
    }

}
