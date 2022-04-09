package com.cimspace.e_library.rest;

import com.cimspace.e_library.model.CategoryDTO;
import com.cimspace.e_library.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/nesera/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategorys() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable final String categoryId) {
        return ResponseEntity.ok(categoryService.get(categoryId));
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createCategory(@RequestBody @Valid final CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable final String categoryId,
            @RequestBody @Valid final CategoryDTO categoryDTO) {
        categoryService.update(categoryId, categoryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final String categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }

}
