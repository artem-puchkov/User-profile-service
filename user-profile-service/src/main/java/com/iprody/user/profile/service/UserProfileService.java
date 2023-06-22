package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.dto.UserDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Temporary mock service (not implemented yet). *
 */
@Service
public final class UserProfileService {

    /**
     * @param userDto {@link UserDto} without id for user creation
     * @return Mono of {@link UserDto} with id of created user. Temporarily returns empty mono.
     */
    public Mono<UserDto> createUser(UserDto userDto) {
        return Mono.empty();
    }

    /**
     * @param userDto {@link UserDto} of the user to update. Must contain id of the user.
     * @return Mono of {@link UserDto} of updated user. Temporarily returns empty mono.
     */
    public Mono<UserDto> updateUser(UserDto userDto) {
        return Mono.empty();
    }

    /**
     * @param userDetailsDto {@link UserDetailsDto} of userDetails to update.
     *                       Must contain id of the user which userDetails we want update
     * @return Mono of updated {@link UserDetailsDto}. Temporarily returns empty mono.
     */
    public Mono<UserDetailsDto> updateUserDetails(UserDetailsDto userDetailsDto) {
        return Mono.empty();
    }

    /**
     * @param id Id of the user we want to find
     * @return Mono of {@link UserDto} of found user. Temporarily returns empty mono.
     */
    public Mono<UserDto> findUser(long id) {
        return Mono.empty();
    }

    /**
     * @param id of the user which userDetails we want to find
     * @return Mono of found {@link UserDetailsDto}. Temporarily returns empty mono.
     */
    public Mono<UserDetailsDto> findUserDetails(long id) {
        return Mono.empty();
    }
}
