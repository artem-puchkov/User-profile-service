package com.iprody.user.profile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

/**
 * User Entity.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    /**
     *  User's id (Primary key).
     */
    @Id
    @GeneratedValue
    private long id;
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
}
