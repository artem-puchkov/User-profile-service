package com.iprody.user.profile.controller;

import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.iprody.user.profile.controller.DummyUserFactory.getValidUserDetailsDto;
import static com.iprody.user.profile.controller.DummyUserFactory.getValidUserDetailsDtoWithId;
import static com.iprody.user.profile.controller.DummyUserFactory.getValidUserDto;
import static com.iprody.user.profile.controller.DummyUserFactory.getValidUserDtoWithId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Mikhail Sheludyakov
 */
@WebFluxTest
class UserProfileControllerTest {

    public static final String PATH_USERS = "/users";
    public static final String PATH_USERS_ID = "/users/{id}";
    public static final String PATH_USERS_ID_DETAILS = "/users/{id}/details";
    public static final long USER_ID = 1L;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    void createUserShouldReturnStatus201WhenSuccessful() {
        when(userProfileService.createUser(getValidUserDto())).thenReturn(Mono.just(getValidUserDtoWithId()));

        webTestClient
                .post()
                .uri(PATH_USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDto()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class).isEqualTo(getValidUserDtoWithId());
    }

    @ParameterizedTest
    @MethodSource("com.iprody.user.profile.controller.DummyUserFactory#getInvalidUserDtoArguments")
    void createInvalidUserShouldReturnStatus400(UserDto userDto) {

        webTestClient
                .post()
                .uri(PATH_USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateUserShouldReturnStatus200WhenSuccessful() {

        when(userProfileService.updateUser(any(UserDto.class)))
                .thenReturn(Mono.just(getValidUserDtoWithId()));

        webTestClient
                .put()
                .uri(PATH_USERS_ID, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDto()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class).isEqualTo(getValidUserDtoWithId());
    }

    @Test
    void updateUserShouldReturnStatus404WhenUserDoesNotExist() {

        when(userProfileService.updateUser(any(UserDto.class)))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        webTestClient
                .put()
                .uri(PATH_USERS_ID, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDto()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @ParameterizedTest
    @MethodSource("com.iprody.user.profile.controller.DummyUserFactory#getInvalidUserDtoArguments")
    void updateUserWithInvalidArgumentsShouldReturnStatus400(UserDto userDto) {
        webTestClient
                .put()
                .uri(PATH_USERS_ID, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateUserDetailsShouldReturnStatus200WhenSuccessful() {
        when(userProfileService.updateUserDetails(any(UserDetailsDto.class)))
                .thenReturn(Mono.just(getValidUserDetailsDtoWithId()));

        webTestClient
                .put()
                .uri(PATH_USERS_ID_DETAILS, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDetailsDto()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDetailsDto.class).isEqualTo(getValidUserDetailsDtoWithId());
    }

    @Test
    void updateUserDetailsShouldReturnStatus404WhenNotFound() {
        when(userProfileService.updateUserDetails(any(UserDetailsDto.class)))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        webTestClient
                .put()
                .uri(PATH_USERS_ID_DETAILS, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidUserDetailsDto()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @ParameterizedTest
    @MethodSource("com.iprody.user.profile.controller.DummyUserFactory#getInvalidUserDetailsDtoArguments")
    void updateUserDetailsWithInvalidArgumentsShouldReturnStatus400(UserDetailsDto userDetailsDto) {
        webTestClient
                .put()
                .uri(PATH_USERS_ID_DETAILS, USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDetailsDto))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void findUserShouldReturnStatus200WhenSuccessful() {
        when(userProfileService.findUser(anyLong()))
                .thenReturn(Mono.just(getValidUserDtoWithId()));

        webTestClient
                .get()
                .uri(PATH_USERS_ID, USER_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class).isEqualTo(getValidUserDtoWithId());
    }

    @Test
    void findUserShouldReturnStatus404WhenNotFound() {
        when(userProfileService.findUser(anyLong()))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        webTestClient
                .get()
                .uri(PATH_USERS_ID, USER_ID)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void findUserDetailsShouldReturnStatus200WhenSuccessful() {
        when(userProfileService.findUserDetails(anyLong()))
                .thenReturn(Mono.just(getValidUserDetailsDtoWithId()));

        webTestClient
                .get()
                .uri(PATH_USERS_ID_DETAILS, USER_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDetailsDto.class).isEqualTo(getValidUserDetailsDtoWithId());
    }

    @Test
    void findUserDetailsShouldReturnStatus404WhenNotFound() {
        when(userProfileService.findUserDetails(anyLong()))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        webTestClient
                .get()
                .uri(PATH_USERS_ID_DETAILS, USER_ID)
                .exchange()
                .expectStatus().isNotFound();
    }
}
