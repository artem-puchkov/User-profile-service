package com.iprody.user.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    /**
     * access token.
     */
    private String accessToken;
    /**
     * refresh token.
     */
    private String refreshToken;
}
