package com.iprody.user.profile.controller;

import com.iprody.user.profile.config.WebSecurityConfig;
import com.iprody.user.profile.dto.CreateUserRequest;
import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.service.UserDetailsService;
import com.iprody.user.profile.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.iprody.user.profile.controller.DummyUserFactory.getValidCreateUserDto;
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
@Import(WebSecurityConfig.class)
@ActiveProfiles("local")
class UserProfileControllerTest {

    public static final String PATH_USERS = "/users";
    public static final String PATH_USERS_ID = "/users/{id}";
    public static final String PATH_USERS_ID_DETAILS = "/users/{id}/details";
    public static final long USER_ID = 1L;

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void createUserShouldReturnStatus201WhenSuccessful() {
        when(userService.save(getValidCreateUserDto())).thenReturn(Mono.just(getValidUserDtoWithId()));

        webTestClient
                .post()
                .uri(PATH_USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getValidCreateUserDto()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class).isEqualTo(getValidUserDtoWithId());
    }

    @ParameterizedTest
    @MethodSource("com.iprody.user.profile.controller.DummyUserFactory#getInvalidUserCreateDtoArguments")
    void createInvalidUserShouldReturnStatus400(CreateUserRequest userDto) {
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
        when(userService.updateUser(any(UserDto.class)))
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
        when(userService.updateUser(any(UserDto.class)))
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
        when(userDetailsService.update(any(UserDetailsDto.class)))
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
        when(userDetailsService.update(any(UserDetailsDto.class)))
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
        when(userService.findUser(anyLong()))
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
        when(userService.findUser(anyLong()))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        webTestClient
                .get()
                .uri(PATH_USERS_ID, USER_ID)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void findUserDetailsShouldReturnStatus200WhenSuccessful() {
        when(userDetailsService.findByUserId(anyLong()))
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
        when(userDetailsService.findByUserId(anyLong()))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        webTestClient
                .get()
                .uri(PATH_USERS_ID_DETAILS, USER_ID)
                .exchange()
                .expectStatus().isNotFound();
    }
}
