package com.iprody.user.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RefreshRequest {

    /**
     * refresh token.
     */
    private String refreshToken;
}
