package com.iprody.user.profile.controller;

import com.iprody.user.profile.dto.CreateUserRequest;
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
     * User valid firstname.
     */
    private static final String FIRSTNAME_OF_USER_1 = "Gregory";
    /**
     * User valid lastname.
     */
    private static final String LASTNAME_OF_USER_1 = "House";
    /**
     * User valid telegramId;.
     */
    private static final String TELEGRAM_OF_USER_1 = "gregtg";
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
    /**
     * Email of user 1.
     */
    private static final String MAIL_OF_USER_1 = "housegregory213@gmail.com";
    /**
     * Id 100.
     */
    private static final long ID100 = 100L;
    /**
     * Password 123456.
     */
    private static final String PASSWORD = "12345Aa+";
    /**
     * Invalid email.
     */
    private static final String INVALID_EMAIL = "email.ru";

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
     * @return Valid UserDto
     */
    public static CreateUserRequest getValidCreateUserDto() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return Valid CreateUserDto for create with email already exists
     */
    public static CreateUserRequest getValidCreateUserDtoWithEmailAlreadyExists() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(MAIL_OF_USER_1)
                .password(PASSWORD)
                .userDetailsDto(getValidUserDetailsForUpdate())
                .build();
    }

    /**
     * @return Valid CreateUserDto for create with email already exists
     */
    public static UserDto getValidUserDtoWithEmailAlreadyExists() {
        return UserDto.builder()
                .firstName(FIRSTNAME_OF_USER_1)
                .lastName(LASTNAME_OF_USER_1)
                .email(MAIL_OF_USER_1)
                .userDetailsDto(getValidUserDetailsForUpdate())
                .build();
    }

    /**
     * @return Valid UserDto for update with id not present in DB
     */
    public static UserDto getValidUserDtoForUpdateWithIdNotPresent() {
        return UserDto.builder()
                .id(ID100)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsForUpdate())
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
     * @return Valid UserDetailDto for successful update
     */
    public static UserDetailsDto getValidUserDetailsForUpdate() {
        return UserDetailsDto.builder()
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
     * @return CreateUserDto with null firstname
     */
    public static CreateUserRequest getCreateUserDtoWithFirstNameNull() {
        return CreateUserRequest.builder()
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with empty firstname
     */
    public static CreateUserRequest getCreateUserDtoWithFirstNameEmpty() {
        return CreateUserRequest.builder()
                .firstName(EMPTY_STRING)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with blank firstname
     */
    public static CreateUserRequest getCreateUserDtoWithFirstNameBlank() {
        return CreateUserRequest.builder()
                .firstName(BLANK_STRING)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with firstname length > 30
     */
    public static CreateUserRequest getCreateUserDtoWithFirstNameLengthGreaterThan30() {
        return CreateUserRequest.builder()
                .firstName(STRING_LONGER_THAN_30)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with null lastname
     */
    public static CreateUserRequest getCreateUserDtoWithLastNameNull() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return UserDto with null lastname
     */
    public static UserDto getUserDtoForUpdateWithLastNameNull() {
        return UserDto.builder()
                .id(USER_ID)
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
     * @return CreateUserDto with empty lastname
     */
    public static CreateUserRequest getCreateUserDtoWithLastNameEmpty() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(EMPTY_STRING)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with blank lastname
     */
    public static CreateUserRequest getCreateUserDtoWithLastNameBlank() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(BLANK_STRING)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with lastname length > 30
     */
    public static CreateUserRequest getCreateUserDtoWithLastNameLengthGreaterThan30() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(STRING_LONGER_THAN_30)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with null email
     */
    public static CreateUserRequest getCreateUserDtoWithEmailNull() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .password(PASSWORD)
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
     * @return CreateUserDto with empty email
     */
    public static CreateUserRequest getCreateUserDtoWithEmailEmpty() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMPTY_STRING)
                .password(PASSWORD)
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
     * @return CreateUserDto with blank email
     */
    public static CreateUserRequest getCreateUserDtoWithEmailBlank() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(BLANK_STRING)
                .password(PASSWORD)
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
                .email(INVALID_EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return CreateUserDto with invalid email
     */
    public static CreateUserRequest getCreateUserDtoWithEmailInvalid() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(INVALID_EMAIL)
                .password(PASSWORD)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return CreateUserDto with password less than 6 characters
     */
    public static CreateUserRequest getCreateUserDtoWithShortPassword() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password("12345")
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return CreateUserDto with password null
     */
    public static CreateUserRequest getCreateUserDtoWithPasswordNull() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .userDetailsDto(getValidUserDetailsDto())
                .build();
    }

    /**
     * @return CreateUserDto with password null
     */
    public static CreateUserRequest getCreateUserDtoWithPasswordBlank() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(BLANK_STRING)
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
     * @return CreateUserDto with null telegramId
     */
    public static CreateUserRequest getCreateUserDtoWithTelegramIdNull() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with invalid telegramId
     */
    public static CreateUserRequest getCreateUserDtoWithTelegramIdBlank() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return CreateUserDto with invalid mobile phone
     */
    public static CreateUserRequest getCreateUserDtoWithMobilePhoneNotMatchesPattern() {
        return CreateUserRequest.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .password(PASSWORD)
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
     * @return Stream of Arguments with invalid UserDto's
     */
    public static Stream<Arguments> getInvalidUserCreateDtoArguments() {
        return Stream.of(
                Arguments.of(getCreateUserDtoWithFirstNameNull()),
                Arguments.of(getCreateUserDtoWithFirstNameEmpty()),
                Arguments.of(getCreateUserDtoWithFirstNameBlank()),
                Arguments.of(getCreateUserDtoWithFirstNameLengthGreaterThan30()),
                Arguments.of(getCreateUserDtoWithLastNameNull()),
                Arguments.of(getCreateUserDtoWithLastNameEmpty()),
                Arguments.of(getCreateUserDtoWithLastNameBlank()),
                Arguments.of(getCreateUserDtoWithLastNameLengthGreaterThan30()),
                Arguments.of(getCreateUserDtoWithEmailNull()),
                Arguments.of(getCreateUserDtoWithEmailEmpty()),
                Arguments.of(getCreateUserDtoWithEmailBlank()),
                Arguments.of(getCreateUserDtoWithEmailInvalid()),
                Arguments.of(getCreateUserDtoWithTelegramIdNull()),
                Arguments.of(getCreateUserDtoWithTelegramIdBlank()),
                Arguments.of(getCreateUserDtoWithMobilePhoneNotMatchesPattern()),
                Arguments.of(getCreateUserDtoWithShortPassword()),
                Arguments.of(getCreateUserDtoWithPasswordBlank()),
                Arguments.of(getCreateUserDtoWithPasswordNull())

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
