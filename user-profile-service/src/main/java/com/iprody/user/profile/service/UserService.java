package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.CreateUserRequest;
import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import com.iprody.user.profile.persistence.UserRepository;
import com.iprody.user.profile.util.ResourceNotFoundException;
import com.iprody.user.profile.util.ResourceProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
    private UserRepository userRepository;
    /**
     * UserMapper for converting between UserDto and UserEntity.
     */
    private UserMapper userMapper;
    /**
     * Injection of password encoder.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Saves a new User entity to the database if the provided email is not already in use.
     *
     * @param userRequest The CreateUserRequest object to be saved as a User entity.
     * @return Mono<UserDto> A Mono emitting the saved User entity as a UserDto object.
     */
    public Mono<UserDto> save(CreateUserRequest userRequest) {
        return userRepository.existsByEmail(userRequest.email())
                ? Mono.error(new ResourceProcessingException(USER_WITH_EMAIL_EXISTS.formatted(userRequest.email())))
                : Mono.fromCallable(() -> {
                    final User user = userMapper.toBusinessModel(userRequest);
                    final UserDetails userDetails = user.getUserDetails();
                    user.setPassword(passwordEncoder.encode(userRequest.password()));
                    userDetails.setUser(user);
                    return userRepository.save(user);
                })
                .map(userMapper::toDto);
    }

    /**
     * Updates an existing User record in the database if the provided id is found.
     *
     * @param userDto The Dto to be updated in the database.
     * @return Mono<UserDto> A Mono emitting the updated User entity as a UserDto object.
     * If the User is not found in the database, an error Mono is returned with an EntityNotFoundException.
     */
    public Mono<UserDto> updateUser(UserDto userDto) {
        return userRepository.existsByEmail(userDto.email())
                ? Mono.error(new ResourceProcessingException(USER_WITH_EMAIL_EXISTS.formatted(userDto.email())))
                : Mono.just(userRepository.findById(userDto.id()))
                .filter(Optional::isPresent)
                .map(existingUser -> {
                    final User userToBeUpdated = userMapper.toBusinessModel(userDto);
                    return userRepository.save(UserMergeUtils.merge(existingUser.get(), userToBeUpdated));
                })
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(USER_NOT_FOUND.formatted(userDto.id()))));
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
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(USER_NOT_FOUND.formatted(id))));
    }
}
