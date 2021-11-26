package com.cimspace.e_library.repos;

import com.cimspace.e_library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
}
