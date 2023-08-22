package com.iprody.user.profile.persistence;

import com.iprody.user.profile.dto.TokenType;
import com.iprody.user.profile.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    /**
     * returns all valid user tokens by their email.
     * @param email users email
     * @return List Entity or Optional.empty()
     */
    List<Token> findByRevokedFalseAndUserEmail(String email);

    /**
     * @param token token jwt token value
     * @return true if token revoked, false if token not revoked or not found
     */
    boolean existsByRevokedFalseAndToken(String token);

    /**
     * @param token token jwt token value
     * @param type - type ACCESS or REFRESH
     * @return true if Token found and Type = type
     */
    boolean existsByTokenAndType(String token, TokenType type);



}
