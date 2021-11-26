package com.cimspace.e_library.repos;

import com.cimspace.e_library.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, String> {
}
