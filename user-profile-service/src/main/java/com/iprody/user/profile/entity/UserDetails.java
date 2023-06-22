package com.iprody.user.profile.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDetails Entity.
 */
@Entity
@Getter
@Setter
public class UserDetails extends AbstractBaseEntity {

    /**
     * Telegram ID to link with a client profile in [telegram.org](<a href="https://telegram.org/">...</a>).
     */
    private String telegramId;

    /**
     * A personal mobile phone number of the user.
     */
    private String mobilePhone;
}
