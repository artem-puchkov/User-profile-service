package com.iprody.user.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthenticationRequest {

    /**
     * Users email.
     */
    private String email;
    /**
     * Raw users password.
     */
    private String password;
}
