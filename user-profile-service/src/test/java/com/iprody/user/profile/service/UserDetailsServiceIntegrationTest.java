package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.UserDetailsDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@IProdyIntegrationTest
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
class UserDetailsServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15.3");

    private static final long USER_ID = 1L;
    private static final long USER_DETAILS_ID = 1L;
    private static final long USER_ID_NOT_EXISTS = 1000L;
    private static final String NEW_TELEGRAM_ID = "@tgId_new";
    private static final String MOBILE_PHONE = "+12345";
    private static final UserDetailsDto USER_DETAILS_DTO = UserDetailsDto.builder()
            .id(USER_DETAILS_ID)
            .telegramId(NEW_TELEGRAM_ID)
            .mobilePhone(MOBILE_PHONE)
            .userId(USER_ID)
            .build();
    private static final String USER_DETAILS_NOT_FOUND_MESSAGE = "No userDetails found with user_id %d"
            .formatted(USER_ID_NOT_EXISTS);
    private static final UserDetailsDto USER_DETAILS_DTO_NOT_EXISTS = UserDetailsDto.builder()
            .telegramId(NEW_TELEGRAM_ID)
            .mobilePhone(MOBILE_PHONE)
            .userId(USER_ID_NOT_EXISTS)
            .build();

    private UserDetailsService userDetailsService;

    @DynamicPropertySource
    private static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    @Transactional
    void updateUserDetailsSuccess() {
        final Mono<UserDetailsDto> userDetailsDtoMono = userDetailsService.update(USER_DETAILS_DTO).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectNext(USER_DETAILS_DTO)
                .verifyComplete();
    }

    @Test
    @Transactional
    void updateUserDetailsIfNotFoundShouldThrowException() {
        final Mono<UserDetailsDto> userDetailsDtoMono =
                userDetailsService.update(USER_DETAILS_DTO_NOT_EXISTS).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectErrorSatisfies(throwable ->
                        assertThat(throwable)
                                .isInstanceOf(EntityNotFoundException.class)
                                .hasNoCause()
                                .hasMessage(USER_DETAILS_NOT_FOUND_MESSAGE))
                .verify();
    }

    @Test
    void findUserDetailsSuccess() {
        final UserDetailsDto expectedDto = UserDetailsDto.builder()
                .id(USER_DETAILS_ID)
                .telegramId("@user")
                .mobilePhone("+111111")
                .userId(USER_ID)
                .build();

        final Mono<UserDetailsDto> userDetailsDtoMono = userDetailsService.findByUserId(USER_ID).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectNext(expectedDto)
                .verifyComplete();
    }

    @Test
    void findUserDetailsIfNotFoundShouldThrowException() {
        final Mono<UserDetailsDto> userDetailsDtoMono =
                userDetailsService.findByUserId(USER_ID_NOT_EXISTS).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectErrorSatisfies(throwable ->
                        assertThat(throwable)
                                .isInstanceOf(EntityNotFoundException.class)
                                .hasNoCause()
                                .hasMessage(USER_DETAILS_NOT_FOUND_MESSAGE))
                .verify();
    }
}
