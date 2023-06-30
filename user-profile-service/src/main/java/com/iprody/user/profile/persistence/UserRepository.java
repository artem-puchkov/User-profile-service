package com.iprody.user.profile.persistence;

import com.iprody.user.profile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @param email The email to check for existence in the database.
     * @return boolean True if the email exists in the database, false otherwise.
     */
    boolean existsByEmail(String email);
}
