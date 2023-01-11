package com.cimspace.e_library.service.implementation;

import com.cimspace.e_library.domain.Category;
import com.cimspace.e_library.model.CategoryDTO;
import com.cimspace.e_library.repos.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .collect(Collectors.toList());
    }

    public CategoryDTO get(final String categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getCategoryId();
    }

    public void update(final String categoryId, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final String categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setName(categoryDTO.getName());
        return category;
    }

}
