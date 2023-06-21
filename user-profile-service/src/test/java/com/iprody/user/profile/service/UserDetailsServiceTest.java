package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import com.iprody.user.profile.persistence.UserDetailsRepository;
import com.iprody.user.profile.service.mapper.UserDetailsMapper;
import com.iprody.user.profile.service.mapper.util.UserDetailsUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserDetailsServiceTest {

    private static final long USER_ID = 1L;
    private static final String TELEGRAM_ID = "@tgId";
    private static final String TELEGRAM_ID_NEW = "@tgId_new";
    private static final long USER_DETAILS_ID = 10L;
    private static final String MOBILE_PHONE = "+555555";
    private static final String MOBILE_PHONE_NEW = "+777777";
    private static final String USER_DETAILS_NOT_FOUND_MESSAGE = "No userDetails found with user_id %d"
            .formatted(USER_ID);
    private static final UserDetailsDto USER_DETAILS_DTO_NEW = UserDetailsDto.builder()
            .id(USER_DETAILS_ID)
            .telegramId(TELEGRAM_ID_NEW)
            .mobilePhone(MOBILE_PHONE_NEW)
            .userId(USER_ID)
            .build();
    private static final UserDetailsDto USER_DETAILS_DTO = UserDetailsDto.builder()
            .id(USER_DETAILS_ID)
            .telegramId(TELEGRAM_ID)
            .mobilePhone(MOBILE_PHONE)
            .userId(USER_ID)
            .build();
    private static final User USER = User.builder()
            .id(USER_ID)
            .firstName("first")
            .lastName("last")
            .build();
    public static final UserDetails USER_DETAILS_NEW = UserDetails.builder()
            .id(USER_DETAILS_ID)
            .telegramId(TELEGRAM_ID_NEW)
            .mobilePhone(MOBILE_PHONE_NEW)
            .user(USER)
            .build();
    public static final UserDetails USER_DETAILS = UserDetails.builder()
            .id(USER_DETAILS_ID)
            .telegramId(TELEGRAM_ID)
            .mobilePhone(MOBILE_PHONE)
            .user(USER)
            .build();

    @Mock
    private UserDetailsRepository userDetailsRepository;
    @Mock
    private UserDetailsMapper userDetailsMapper;
    @InjectMocks
    private UserDetailsService userDetailsService;

    @Test
    void updateUserDetailsSuccess() {
        final UserDetails mergedUserDetails = UserDetailsUtils.merge(USER_DETAILS, USER_DETAILS_NEW);

        when(userDetailsRepository.findByUserId(USER_ID)).thenReturn(Optional.of(USER_DETAILS));
        when(userDetailsMapper.toBusinessModel(USER_DETAILS_DTO_NEW)).thenReturn(USER_DETAILS_NEW);
        when(userDetailsRepository.save(mergedUserDetails)).thenReturn(mergedUserDetails);
        when(userDetailsMapper.toDto(mergedUserDetails)).thenReturn(USER_DETAILS_DTO_NEW);

        final Mono<UserDetailsDto> userDetailsDtoMono = userDetailsService.update(USER_DETAILS_DTO_NEW).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectNext(USER_DETAILS_DTO_NEW)
                .verifyComplete();
    }

    @Test
    void updateUserDetailsIfNotFoundShouldThrowException() {
        when(userDetailsRepository.findByUserId(anyLong()))
                .thenThrow(new EntityNotFoundException(USER_DETAILS_NOT_FOUND_MESSAGE));

        final Mono<UserDetailsDto> userDetailsDtoMono = userDetailsService.update(USER_DETAILS_DTO_NEW).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectErrorSatisfies(throwable ->
                        assertThat(throwable)
                                .isInstanceOf(EntityNotFoundException.class)
                                .hasNoCause()
                                .hasMessage(USER_DETAILS_NOT_FOUND_MESSAGE))
                .verify();
    }

    @Test
    void findUserDetailsSuccess() {
        when(userDetailsRepository.findByUserId(USER_ID)).thenReturn(Optional.of(USER_DETAILS));
        when(userDetailsMapper.toDto(USER_DETAILS)).thenReturn(USER_DETAILS_DTO);

        final Mono<UserDetailsDto> userDetailsDtoMono = userDetailsService.findByUserId(USER_ID).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectNext(USER_DETAILS_DTO)
                .verifyComplete();
    }

    @Test
    void findUserDetailsIfNotFoundShouldThrowException() {
        when(userDetailsRepository.findByUserId(anyLong()))
                .thenThrow(new EntityNotFoundException(USER_DETAILS_NOT_FOUND_MESSAGE));

        final Mono<UserDetailsDto> userDetailsDtoMono = userDetailsService.findByUserId(USER_ID).log();

        StepVerifier.create(userDetailsDtoMono)
                .expectErrorSatisfies(throwable ->
                        assertThat(throwable)
                                .isInstanceOf(EntityNotFoundException.class)
                                .hasNoCause()
                                .hasMessage(USER_DETAILS_NOT_FOUND_MESSAGE))
                .verify();
    }
}
