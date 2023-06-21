package com.iprody.user.profile.mapper;

import com.iprody.user.profile.dto.UserDetailsDto;
import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import com.iprody.user.profile.service.mapper.UserDetailsMapper;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;

@ExtendWith(SoftAssertionsExtension.class)
class UserDetailsMapperTest {

    private static final String TELEGRAM_ID = "@tg";
    private static final String MOBILE_PHONE = "+123456";
    private static final long USER_ID = 2L;
    private static final long USER_DETAILS_ID = 1L;
    private static final User USER = User.builder()
            .id(USER_ID)
            .build();
    private static final UserDetails USER_DETAILS = UserDetails.builder()
            .id(USER_DETAILS_ID)
            .telegramId(TELEGRAM_ID)
            .mobilePhone(MOBILE_PHONE)
            .user(USER)
            .build();
    private static final UserDetailsDto USER_DETAILS_DTO = UserDetailsDto.builder()
            .userId(USER_ID)
            .id(USER_DETAILS_ID)
            .mobilePhone(MOBILE_PHONE)
            .telegramId(TELEGRAM_ID)
            .build();

    private final UserDetailsMapper mapper = Mappers.getMapper(UserDetailsMapper.class);

    @Test
    void mapEntityToDto(SoftAssertions softly) {
        final UserDetailsDto actual = mapper.toDto(USER_DETAILS);

        softly.assertThat(actual.id()).isEqualTo(USER_DETAILS_DTO.id());
        softly.assertThat(actual.userId()).isEqualTo(USER_DETAILS_DTO.userId());
        softly.assertThat(actual.telegramId()).isEqualTo(USER_DETAILS_DTO.telegramId());
        softly.assertThat(actual.mobilePhone()).isEqualTo(USER_DETAILS_DTO.mobilePhone());
    }

    @Test
    void mapDtoToEntity(SoftAssertions softly) {
        final UserDetails actual = mapper.toBusinessModel(USER_DETAILS_DTO);

        softly.assertThat(actual.getId()).isEqualTo(USER_DETAILS_ID);
        softly.assertThat(actual.getTelegramId()).isEqualTo(TELEGRAM_ID);
        softly.assertThat(actual.getMobilePhone()).isEqualTo(MOBILE_PHONE);
    }
}
