package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import com.iprody.user.profile.persistence.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserService {

    /**
     * Template for the error message when a user is not found.
     */
    private static final String USER_NOT_FOUND = "No user found with id: %d";
    /**
     * Template for the error message when a user with such email exists.
     */
    private static final String USER_WITH_EMAIL_EXISTS = "A user with email: %s already exists.";
    /**
     * UserRepository that this service interacts with.
     */
    private final UserRepository userRepository;
    /**
     * UserMapper for converting between UserDto and UserEntity.
     */
    private final UserMapper userMapper;

    /**
     * Saves a new User entity to the database if the provided email is not already in use.
     *
     * @param userDto The UserDto object to be saved as a User entity.
     * @return Mono<UserDto> A Mono emitting the saved User entity as a UserDto object.
     */
    public Mono<UserDto> save(UserDto userDto) {
        return Mono.fromCallable(() -> userRepository.existsByEmail(userDto.email()))
                .filter(existingUser -> !existingUser)
                .map(noneExistingUser -> {
                    final User userEntity = userMapper.toBusinessModel(userDto);
                    final UserDetails userDetails = userEntity.getUserDetails();
                    userDetails.setUser(userEntity);
                    return userRepository.save(userEntity);
                })
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityExistsException(
                        USER_WITH_EMAIL_EXISTS.formatted(userDto.email()))));
    }

    /**
     * Updates an existing User record in the database if the provided id is found.
     *
     * @param userDto The Dto to be updated in the database.
     * @return Mono<UserDto> A Mono emitting the updated User entity as a UserDto object.
     * If the User is not found in the database, an error Mono is returned with an EntityNotFoundException.
     */
    public Mono<UserDto> updateUser(UserDto userDto) {
        return Mono.fromCallable(() -> userRepository.findById(userDto.id()))
                .filter(Optional::isPresent)
                .map(existingUser -> {
                    final User userToBeUpdated = userMapper.toBusinessModel(userDto);
                    return userRepository.save(UserMergeUtils.merge(existingUser.get(), userToBeUpdated));
                })
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(USER_NOT_FOUND.formatted(userDto.id()))));
    }

    /**
     * Finds a User entity in the database based on the provided id.
     *
     * @param id The id of the User to be found.
     * @return Mono<UserDto> A Mono emitting the found User entity as a Dto object.
     * If the User is not found in the database, an error Mono is returned with an EntityNotFoundException.
     */
    public Mono<UserDto> findUser(Long id) {
        return Mono.fromCallable(() -> userRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(USER_NOT_FOUND.formatted(id))));
    }
}
