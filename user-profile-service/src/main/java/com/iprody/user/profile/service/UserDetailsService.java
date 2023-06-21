package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.entity.UserDetails;
import com.iprody.user.profile.persistence.UserDetailsRepository;
import com.iprody.user.profile.service.mapper.UserDetailsMapper;
import com.iprody.user.profile.service.mapper.util.UserDetailsUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserDetailsService {
    /**
     * Error message if userDetails was not found.
     */
    private static final String USER_DETAILS_NOT_FOUND = "No userDetails found with user_id %d";
    /**
     * Injection of repository for retrieving data.
     */
    private UserDetailsRepository userDetailsRepository;
    /**
     * Mapper injection.
     */
    private UserDetailsMapper userDetailsMapper;

    /**
     * @param userDetailsDto Dto with info for update. Must contain corresponding userId.
     * @return Dto with updated information or Mono.error() if userDetailsDto was not found.
     */
    @Transactional
    public Mono<UserDetailsDto> update(UserDetailsDto userDetailsDto) {
        return Mono.fromCallable(() -> userDetailsRepository.findByUserId(userDetailsDto.userId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userDetailsForUpdate -> {
                    final UserDetails userDetailsFrom = userDetailsMapper.toBusinessModel(userDetailsDto);
                    return UserDetailsUtils.merge(userDetailsForUpdate, userDetailsFrom);
                })
                .map(userDetailsRepository::save)
                .map(userDetailsMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(
                                USER_DETAILS_NOT_FOUND.formatted(userDetailsDto.userId())
                        ))
                );
    }

    /**
     * @param id of the user which userDetails we want to find.
     * @return Dto of found userDetails or Mono.error() if userDetails was not found.
     */
    public Mono<UserDetailsDto> findByUserId(long id) {
        return Mono.fromCallable(() -> userDetailsRepository.findByUserId(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userDetailsMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(
                        USER_DETAILS_NOT_FOUND.formatted(id))
                ));
    }
}
