package com.iprody.user.profile.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * User Entity.
 */
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractBaseEntity {

    /**
     * User's first name.
     */
    private String firstName;
    /**
     * User's first name.
     */
    private String lastName;
    /**
     * User's email.
     */
    private String email;
    /**
     * Table user_details have user_id FK that references user.id.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetails userDetails;
}
