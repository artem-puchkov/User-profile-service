package com.iprody.user.profile.persistence;

import com.iprody.user.profile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @param email The email to check for existence in the database.
     * @return boolean True if the email exists in the database, false otherwise.
     */
    boolean existsByEmail(String email);
    /**
     * @param email The email to check for existence in the database.
     * @return Entity or Optional.empty.
     */
    Optional<User> findByEmail(String email);
}
