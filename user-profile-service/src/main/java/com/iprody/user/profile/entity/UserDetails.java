package com.iprody.user.profile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * UserDetails Entity.
 */
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class UserDetails extends AbstractBaseEntity {

    /**
     * Telegram ID to link with a client profile in [telegram.org](<a href="https://telegram.org/">...</a>).
     */
    private String telegramId;

    /**
     * A personal mobile phone number of the user.
     */
    private String mobilePhone;

    /**
     * UserDetails owns the association and user_details table have user_id FK.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;
}
