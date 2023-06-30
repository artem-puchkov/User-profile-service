package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.UserDto;
import com.iprody.user.profile.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserDetailsMapper.class)
public interface UserMapper {

    /**
     * @param userDto UserDto object to be converted to a User entity.
     * @return User entity converted from the provided UserDto object.
     */
    @Mapping(source = "userDetailsDto", target = "userDetails")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "userDetails.created", ignore = true)
    @Mapping(target = "userDetails.updated", ignore = true)
    @Mapping(target = "userDetails.status", ignore = true)
    @Mapping(target = "userDetails.user", ignore = true)
    User toBusinessModel(UserDto userDto);

    /**
     * @param user User entity to be converted to a UserDto object.
     * @return UserDto object converted from the provided User entity.
     */
    @Mapping(source = "userDetails", target = "userDetailsDto")
    @Mapping(source = "userDetails.user.id", target = "userDetailsDto.userId")
    UserDto toDto(User user);
}
