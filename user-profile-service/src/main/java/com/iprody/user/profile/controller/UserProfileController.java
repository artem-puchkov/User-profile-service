package com.iprody.user.profile.controller;

import com.iprody.user.profile.dto.CreateUserRequest;
import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.service.UserDetailsService;
import com.iprody.user.profile.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller that exposes endpoints for User and UserDetails on path '/api/v1/users'.
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserProfileController {

    /**
     * Injection of service with business logic of userDetails.
     */
    private UserDetailsService userDetailsService;
    /**
     * Injection of service with business logic of user.
     * (Should be implemented in different task)
     */
    private UserService userService;

    /**
     * @param userRequest {@link CreateUserRequest} without id for user creation
     * @return Mono of {@link UserDto} with id of created user
     */
    @Operation(summary = "Create user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> createUser(@RequestBody @Valid CreateUserRequest userRequest) {
        log.debug("Start create user: {}", userRequest);
        return userService.save(userRequest);
    }

    /**
     * @param id      Id of the user to update
     * @param userDto {@link UserDto} of the user to update. Must contain id of the user.
     * @return Mono of {@link UserDto} of updated user
     */
    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> updateUser(@PathVariable long id,
                                    @RequestBody @Valid UserDto userDto) {
        log.debug("Start update user: {}", userDto);
        return userService.updateUser(userDto);
    }

    /**
     * @param id             Id of the user which {@link UserDetailsDto} will be updated
     * @param userDetailsDto {@link UserDetailsDto} of userDetails to update.
     *                       Must contain id of the user which userDetails we want update
     * @return Mono of updated {@link UserDetailsDto}
     */
    @Operation(summary = "Update userdetails")
    @PutMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDetailsDto> updateUserDetails(@PathVariable long id,
                                                  @RequestBody @Valid UserDetailsDto userDetailsDto) {
        log.debug("Start update user details: {}", userDetailsDto);
        return userDetailsService.update(userDetailsDto);
    }

    /**
     * @param id Id of the user we want to find
     * @return Mono of {@link UserDto} of found user
     */
    @Operation(summary = "Find user")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> findUser(@PathVariable long id) {
        log.debug("Find user with id: {}", id);
        return userService.findUser(id);
    }

    /**
     * @param id of the user which userDetails we want to find
     * @return Mono of found {@link UserDetailsDto}
     */
    @Operation(summary = "Find userdetails")
    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDetailsDto> findUserDetails(@PathVariable long id) {
        log.debug("Find userDetails with userId: {}", id);
        return userDetailsService.findByUserId(id);
    }
}
