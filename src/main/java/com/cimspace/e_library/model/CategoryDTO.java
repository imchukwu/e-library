package com.cimspace.e_library.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CategoryDTO {

    @NotNull
    @Size(max = 255)
    private String categoryId;

    @Size(max = 255)
    private String name;

    public CategoryDTO(){}

    public CategoryDTO(@NotNull @Size(max = 255) String categoryId, @Size(max = 255) String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "categoryId='" + categoryId + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
