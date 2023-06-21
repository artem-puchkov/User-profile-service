package com.iprody.user.profile.service.mapper;

import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {

    /**
     * @param userDetailsDto Dto that holds information to update.
     * @return userDetails that holds info for update
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "status", ignore = true)
    UserDetails toBusinessModel(UserDetailsDto userDetailsDto);

    /**
     * @param userDetails {@link UserDetails} entity that we want to map to dto
     * @return {@link UserDetailsDto} - mapping result from userDetails entity.
     * (Maps from userDetails.user field to userDetailsDto.user_id field.)
     */
    @Mapping(target = "userId", source = "user")
    UserDetailsDto toDto(UserDetails userDetails);

    /**
     * Support method for user->userId mapping.
     *
     * @param user User which id we want to map
     * @return UserId
     */
    default long getUserId(User user) {
        return user.getId();
    }
}
