package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.CreateUserRequest;
import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.util.ResourceNotFoundException;
import com.iprody.user.profile.util.ResourceProcessingException;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.test.StepVerifier;

@IProdyIntegrationTest
@RequiredArgsConstructor
@ExtendWith(SoftAssertionsExtension.class)
class UserServiceIntegrationTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    private static final long NON_EXISTENT_USER_ID = 99L;
    private static final String TEST_FIRST_NAME = "Gregory";
    private static final String TEST_LAST_NAME = "House";
    private static final String UPDATED_FIRST_NAME = "UpdatedFirst";
    private static final String UPDATED_LAST_NAME = "UpdatedLast";
    private static final String TEST_EMAIL = "updated@test.com";
    private static final String NON_EXISTENT_USER = "NonExistentUser";
    private static final String NON_EXISTENT_USER_EMAIL = "nonexistent@test.com";
    private static final String TELEGRAM_ID = "TestId";
    private static final String PASSWORD = "123456";
    private static final String USER_NOT_FOUND_MESSAGE = "No user found with id: %d"
            .formatted(NON_EXISTENT_USER_ID);
    private static final String EXISTING_USER_EMAIL = "housegregory213@gmail.com";
    private static final String ENTITY_EXISTS_MESSAGE = "A user with email: %s already exists."
            .formatted(EXISTING_USER_EMAIL);

    private final UserService userService;

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void whenCreateUserSuccess(SoftAssertions softly) {
        final UserDetailsDto userDetailsDto = new UserDetailsDto(null, TELEGRAM_ID, null, null);
        final CreateUserRequest userDto =
                new CreateUserRequest(null, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL, PASSWORD, userDetailsDto);

        StepVerifier.create(userService.save(userDto))
                .assertNext(createdUserDto -> {
                    softly.assertThat(createdUserDto.id()).isNotNull();
                    softly.assertThat(createdUserDto.firstName()).isEqualTo(TEST_FIRST_NAME);
                    softly.assertThat(createdUserDto.lastName()).isEqualTo(TEST_LAST_NAME);
                    softly.assertThat(createdUserDto.email()).isEqualTo(TEST_EMAIL);
                    softly.assertThat(createdUserDto.userDetailsDto().telegramId()).isEqualTo(TELEGRAM_ID);
                })
                .verifyComplete();
    }

    @Test
    void whenCreateUserWithExistingEmailFail(SoftAssertions softly) {
        final UserDetailsDto userDetailsDto = new UserDetailsDto(null, TELEGRAM_ID, null, null);
        final CreateUserRequest newUserDto = new CreateUserRequest(
                null, UPDATED_FIRST_NAME, UPDATED_LAST_NAME, EXISTING_USER_EMAIL, PASSWORD, userDetailsDto);

        StepVerifier.create(userService.save(newUserDto))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(ResourceProcessingException.class)
                        .hasMessage(ENTITY_EXISTS_MESSAGE))
                .verify();
    }

    @Test
    void whenUpdateUserWithExistingEmailFail(SoftAssertions softly) {
        final Long userId = 2L;
        final UserDetailsDto userDetailsDto = new UserDetailsDto(userId, TELEGRAM_ID, null, null);
        final UserDto newUserDto = new UserDto(
                userId, UPDATED_FIRST_NAME, UPDATED_LAST_NAME, EXISTING_USER_EMAIL, userDetailsDto);

        StepVerifier.create(userService.updateUser(newUserDto))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(ResourceProcessingException.class)
                        .hasMessage(ENTITY_EXISTS_MESSAGE))
                .verify();
    }

    @Test
    void whenUpdateUserSuccess(SoftAssertions softly) {
        final Long userId = 2L;
        final UserDetailsDto userDetailsDto = new UserDetailsDto(userId, TELEGRAM_ID, null, null);
        final String uniqueTestEmail = "updated" + userId + "@test.com";
        final UserDto userDto = new UserDto(
                userId, UPDATED_FIRST_NAME, UPDATED_LAST_NAME, uniqueTestEmail, userDetailsDto);

        StepVerifier.create(userService.updateUser(userDto))
                .assertNext(updatedUserDto -> {
                    softly.assertThat(updatedUserDto.id()).isEqualTo(userId);
                    softly.assertThat(updatedUserDto.firstName()).isEqualTo(UPDATED_FIRST_NAME);
                    softly.assertThat(updatedUserDto.lastName()).isEqualTo(UPDATED_LAST_NAME);
                    softly.assertThat(updatedUserDto.email()).isEqualTo(uniqueTestEmail);
                })
                .verifyComplete();
    }

    @Test
    void whenUpdateUserNotFound(SoftAssertions softly) {
        final UserDetailsDto userDetailsDto = new UserDetailsDto(null, TELEGRAM_ID, null, null);
        final UserDto userDto = new UserDto(
                NON_EXISTENT_USER_ID, NON_EXISTENT_USER, NON_EXISTENT_USER, NON_EXISTENT_USER_EMAIL, userDetailsDto);

        StepVerifier.create(userService.updateUser(userDto))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage(USER_NOT_FOUND_MESSAGE))
                .verify();
    }

    @Test
    void whenFindUserFound(SoftAssertions softly) {
        StepVerifier.create(userService.findUser(1L))
                .assertNext(userDto -> {
                    softly.assertThat(userDto).isNotNull();
                    softly.assertThat(userDto.firstName()).isEqualTo(TEST_FIRST_NAME);
                    softly.assertThat(userDto.lastName()).isEqualTo(TEST_LAST_NAME);
                })
                .verifyComplete();
    }

    @Test
    void whenFindUserNotFound(SoftAssertions softly) {
        StepVerifier.create(userService.findUser(NON_EXISTENT_USER_ID))
                .expectErrorSatisfies(throwable -> softly.assertThat(throwable)
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage(USER_NOT_FOUND_MESSAGE))
                .verify();
    }
}
