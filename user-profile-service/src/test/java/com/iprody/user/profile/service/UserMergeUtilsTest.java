package com.iprody.user.profile.service;

import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class UserMergeUtilsTest {

    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final String USER_EMAIL = "john.doe@example.com";
    private static final String USER_FIRST_NAME_NEW = "Jane";
    private static final String USER_LAST_NAME_NEW = "Smith";
    private static final String USER_EMAIL_NEW = "jane.smith@example.com";
    private static final long USER_DETAILS_ID = 2L;
    private static final User USER = User.builder()
            .id(1L)
            .build();
    private static final long INCORRECT_USER_DETAILS_ID = 1000L;
    private static final String TELEGRAM_ID = "@tgId";
    private static final String TELEGRAM_ID_NEW = "@tgId_new";
    private static final String MOBILE_PHONE_NEW = "+55555";

    @Test
    void mergeUsers(SoftAssertions softly) {
        final UserDetails userDetails = UserDetails.builder()
                .id(USER_DETAILS_ID)
                .telegramId(TELEGRAM_ID)
                .build();
        final User userTo = User.builder()
                .id(1L)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .email(USER_EMAIL)
                .userDetails(userDetails)
                .build();
        final UserDetails userDetailsNew = UserDetails.builder()
                .id(USER_DETAILS_ID)
                .telegramId(TELEGRAM_ID_NEW)
                .mobilePhone(MOBILE_PHONE_NEW)
                .build();
        final User userFrom = User.builder()
                .id(1L)
                .firstName(USER_FIRST_NAME_NEW)
                .lastName(USER_LAST_NAME_NEW)
                .email(USER_EMAIL_NEW)
                .userDetails(userDetailsNew)
                .build();

        final User actual = UserMergeUtils.merge(userTo, userFrom);

        softly.assertThat(actual.getId()).isEqualTo(userTo.getId());
        softly.assertThat(actual.getFirstName()).isEqualTo(userFrom.getFirstName());
        softly.assertThat(actual.getLastName()).isEqualTo(userFrom.getLastName());
        softly.assertThat(actual.getEmail()).isEqualTo(userFrom.getEmail());
        softly.assertThat(actual.getUserDetails().getTelegramId()).isEqualTo(userFrom.getUserDetails().getTelegramId());
        softly.assertThat(actual.getUserDetails().getMobilePhone())
                .isEqualTo(userFrom.getUserDetails().getMobilePhone());
    }

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

        final UserDetails actual = UserMergeUtils.merge(userDetailsTo, userDetailsFrom);

        softly.assertThat(actual.getId()).isEqualTo(userDetailsMerged.getId());
        softly.assertThat(actual.getUser().getId()).isEqualTo(userDetailsMerged.getUser().getId());
        softly.assertThat(actual.getTelegramId()).isEqualTo(userDetailsMerged.getTelegramId());
        softly.assertThat(actual.getMobilePhone()).isEqualTo(userDetailsMerged.getMobilePhone());
    }
}
