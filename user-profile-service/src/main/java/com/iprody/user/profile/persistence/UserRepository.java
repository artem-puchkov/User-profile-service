package com.iprody.user.profile.persistence;

import com.iprody.user.profile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
