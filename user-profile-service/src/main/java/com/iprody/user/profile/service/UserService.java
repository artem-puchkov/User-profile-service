package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.UserDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * To be implemented in different task.
 */
@Service
public class UserService {

    /**
     * Saves a new User entity to the database.
     *
     * @param userDto The UserDto object to be saved as a User entity.
     * @return Mono<UserDto> A Mono emitting the saved User entity as a UserDto object.
     */

    public Mono<UserDto> save(UserDto userDto) {
        return Mono.empty();
    }

    /**
     * Updates an existing User entity in the database.
     *
     * @param userDto The UserDto object to be updated in the database.
     * @return Mono<UserDto> A Mono emitting the updated User entity as a UserDto object.
     * If the User is not found in the database, an error Mono is returned with an EntityNotFoundException.
     */
    public Mono<UserDto> updateUser(UserDto userDto) {
        return Mono.empty();
    }

    /**
     * Finds a User entity in the database based on the provided id.
     *
     * @param id The id of the User to be found.
     * @return Mono<UserDto> A Mono emitting the found User entity as a UserDto object.
     * If the User is not found in the database, an error Mono is returned with an EntityNotFoundException.
     */
    public Mono<UserDto> findUser(Long id) {
        return Mono.empty();
    }
}
