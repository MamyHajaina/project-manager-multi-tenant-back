package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByEmailAndPasswordHash(String email, String passwordHash);

    boolean existsByEmail(String email);
}
