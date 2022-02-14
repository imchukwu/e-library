package com.cimspace.e_library.repos;

import com.cimspace.e_library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, String> {
}
