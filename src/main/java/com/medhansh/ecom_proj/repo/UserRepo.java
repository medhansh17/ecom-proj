package com.medhansh.ecom_proj.repo;

import com.medhansh.ecom_proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
