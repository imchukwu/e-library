package com.cimspace.e_library.repos;

import com.cimspace.e_library.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, String> {
}
