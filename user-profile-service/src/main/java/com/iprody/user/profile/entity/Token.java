package com.iprody.user.profile.entity;

import com.iprody.user.profile.dto.TokenType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "authentication_tokens")
public class Token extends AbstractBaseEntity {

    /**
     * JWT token value.
     */
    private String token;
    /**
     * Token status.
     */
    private boolean revoked;
    /**
     * Token type.
     */
    @Enumerated(EnumType.STRING)
    private TokenType type;
    /**
     * Token owns the association and jwt_tokens table have user_id FK.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
