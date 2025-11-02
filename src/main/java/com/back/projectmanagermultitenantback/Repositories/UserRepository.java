package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
