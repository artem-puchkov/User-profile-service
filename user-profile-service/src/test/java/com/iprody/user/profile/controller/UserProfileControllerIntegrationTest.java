package com.iprody.user.profile.controller;

import com.iprody.user.profile.service.IProdyIntegrationTest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static com.iprody.user.profile.controller.DummyUserFactory.getUserDtoForUpdateWithLastNameNull;
import static com.iprody.user.profile.controller.DummyUserFactory.getValidUserDtoForCreateWithEmailAlreadyExists;
import static com.iprody.user.profile.controller.DummyUserFactory.getValidUserDtoForUpdateWithIdNotPresent;

@IProdyIntegrationTest
@AutoConfigureWebTestClient
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
class UserProfileControllerIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:15.3");

    private static final String UPDATE_USER_1 = "/users/1";
    private static final String USERS = "/users";
    private static final String JSON_MESSAGE = "$.message";
    private static final String JSON_DETAILS = "$.details";
    private static final String JSON_STATUS = "$.status";

    private WebTestClient webTestClient;

    @DynamicPropertySource
    private static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void checkResourceNotFoundException404() {
        webTestClient
                .put()
                .uri(UPDATE_USER_1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDtoForUpdateWithIdNotPresent()))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath(JSON_MESSAGE).isEqualTo("Resource was not found")
                .jsonPath(JSON_DETAILS).isEqualTo("No user found with id: 100")
                .jsonPath(JSON_STATUS).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void checkResourceProcessingException500() {
        webTestClient
                .post()
                .uri(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDtoForCreateWithEmailAlreadyExists()))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath(JSON_MESSAGE).isEqualTo("Error occurred during processing the resource")
                .jsonPath(JSON_STATUS).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .jsonPath(JSON_DETAILS).isEqualTo("A user with email: test@test.com already exists.");
    }

    @Test
    void checkWebExchangeBindException400() {
        webTestClient
                .put()
                .uri(UPDATE_USER_1)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getUserDtoForUpdateWithLastNameNull()))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath(JSON_MESSAGE).isEqualTo("Request validation error occurred")
                .jsonPath(JSON_DETAILS).isEqualTo("Last name should not be empty")
                .jsonPath(JSON_STATUS).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
