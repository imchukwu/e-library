package com.cimspace.e_library.repos;

import com.cimspace.e_library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
   Optional<Boolean> findByEmail(String email);
}
