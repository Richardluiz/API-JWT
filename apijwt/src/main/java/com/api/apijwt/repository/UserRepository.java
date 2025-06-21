package com.api.apijwt.repository;

import com.api.apijwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
     List<User> findAllByUsername(String username);
}