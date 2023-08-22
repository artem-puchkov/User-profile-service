package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.CreateUserRequest;
import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserDetailsMapper.class)
public interface UserMapper {

    /**
     * Mapping method for user update.
     *
     * @param userDto UserDto object to be converted to a User entity.
     * @return User entity converted from the provided UserDto object.
     */
    @Mapping(target = "userDetails", source = "userDetailsDto")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "userDetails.created", ignore = true)
    @Mapping(target = "userDetails.updated", ignore = true)
    @Mapping(target = "userDetails.user", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    User toBusinessModel(UserDto userDto);

    /**
     * Mapping method for user creation.
     *
     * @param createUserRequest object to be converted to a User entity.
     * @return User entity converted from the provided CreateUserDto object.
     */
    @Mapping(target = "userDetails", source = "userDetailsDto")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "userDetails.id", ignore = true)
    @Mapping(target = "userDetails.created", ignore = true)
    @Mapping(target = "userDetails.updated", ignore = true)
    @Mapping(target = "userDetails.user", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    User toBusinessModel(CreateUserRequest createUserRequest);

    /**
     * @param user User entity to be converted to a UserDto object.
     * @return UserDto object converted from the provided User entity.
     */
    @Mapping(source = "userDetails", target = "userDetailsDto")
    @Mapping(source = "userDetails.user.id", target = "userDetailsDto.userId")
    UserDto toDto(User user);
}
