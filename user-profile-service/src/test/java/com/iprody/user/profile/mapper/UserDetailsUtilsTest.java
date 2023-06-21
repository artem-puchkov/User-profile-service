package com.iprody.user.profile.mapper;

import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import com.iprody.user.profile.service.mapper.util.UserDetailsUtils;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class UserDetailsUtilsTest {

    private static final long USER_DETAILS_ID = 2L;
    private static final User USER = User.builder()
            .id(1L)
            .build();
    private static final long INCORRECT_USER_DETAILS_ID = 1000L;
    private static final String TELEGRAM_ID = "@tgId";
    private static final String TELEGRAM_ID_NEW = "@tgId_new";
    private static final String MOBILE_PHONE_NEW = "+55555";

    @Test
    void merge(SoftAssertions softly) {
        final UserDetails userDetailsTo = UserDetails.builder()
                .id(USER_DETAILS_ID)
                .user(USER)
                .telegramId(TELEGRAM_ID)
                .build();
        final UserDetails userDetailsFrom = UserDetails.builder()
                .id(INCORRECT_USER_DETAILS_ID)
                .user(USER)
                .telegramId(TELEGRAM_ID_NEW)
                .mobilePhone(MOBILE_PHONE_NEW)
                .build();
        final UserDetails userDetailsMerged = UserDetails.builder()
                .id(USER_DETAILS_ID)
                .user(USER)
                .telegramId(TELEGRAM_ID_NEW)
                .mobilePhone(MOBILE_PHONE_NEW)
                .build();

        final UserDetails actual = UserDetailsUtils.merge(userDetailsTo, userDetailsFrom);

        softly.assertThat(actual.getId()).isEqualTo(userDetailsMerged.getId());
        softly.assertThat(actual.getUser().getId()).isEqualTo(userDetailsMerged.getUser().getId());
        softly.assertThat(actual.getTelegramId()).isEqualTo(userDetailsMerged.getTelegramId());
        softly.assertThat(actual.getMobilePhone()).isEqualTo(userDetailsMerged.getMobilePhone());
    }
}
