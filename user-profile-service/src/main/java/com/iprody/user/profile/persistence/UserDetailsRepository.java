package com.iprody.user.profile.persistence;

import com.iprody.user.profile.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    /**
     * @param id UserId for searching
     * @return Entity with given userId or Optional.empty()
     */
    Optional<UserDetails> findByUserId(Long id);
}
