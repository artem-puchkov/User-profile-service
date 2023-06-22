package com.iprody.user.profile.entity;

/**
 * A status of the entity in a persistent layer.
 */
public enum Status {
    /**
     * Reflects that a persistent entity is active.
     */
    ACTIVE,

    /**
     * Indicates that the entity is not active.
     */
    NOT_ACTIVE,

    /**
     * Indicates that the entity has been removed.
     */
    DELETED
}
