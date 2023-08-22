package com.iprody.user.profile.service;

import com.iprody.user.profile.dto.TokenType;
import com.iprody.user.profile.entity.Token;
import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.persistence.TokenRepository;
import com.iprody.user.profile.persistence.UserRepository;
import com.iprody.user.profile.util.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    /**
     * Error message if user not found.
     */
    private static final String USER_NOT_FOUND = "User not found";
    /**
     * TokenService for saving and updating tokens in database.
     */
    private final TokenRepository tokenRepository;
    /**
     * UserRepository for extracting the user's entity.
     */
    private final UserRepository userRepository;

    /**
     * Save Tokens in DB.
     * @param requestToken - token
     * @param email - users email
     * @param type - REFRESH or ACCESS type
     */
    public void save(String requestToken, String email, TokenType type) {
        final User user = userRepository.findByEmail(email).orElseThrow(() -> new AuthException(USER_NOT_FOUND));
        final Token token = Token.builder()
                .token(requestToken)
                .user(user)
                .type(type)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Cancels all tokens previously issued to the user.
     * @param email - users email
     */
    public void revokeAllUserTokens(String email) {
        final var validUserTokens = tokenRepository.findByRevokedFalseAndUserEmail(email);
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Chek tokens Type.
     * @param expectedToken - accepted token
     * @return true if token has refresh type
     */
    public boolean isRefreshToken(String expectedToken) {
        return tokenRepository.existsByTokenAndType(expectedToken, TokenType.REFRESH);
    }

    /**
     * Chek tokens status.
     * @param expectedToken - accepted token
     * @return true if token revoked
     */
    public boolean isNotRevokedToken(String expectedToken) {
        return tokenRepository.existsByRevokedFalseAndToken(expectedToken);
    }
}
