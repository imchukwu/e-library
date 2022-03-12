package com.cimspace.e_library.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


public class AuthorDTO {

    @NotNull
    @Size(max = 255)
    private String authorId;

    @Size(max = 255)
    private String name;

    private Set<BookDTO> authorBooks;

    public Set<BookDTO> getAuthorBooks() {
        return authorBooks;
    }

    public void setAuthorBooks(Set<BookDTO> authorBooks) {
        this.authorBooks = authorBooks;
    }

    public String getAuthorId() {
        return authorId;
    }

    public AuthorDTO(){
    }

    public AuthorDTO(String authorId, @Size(max = 255) String name) {
        this.authorId = authorId;
        this.name = name;
    }

    public AuthorDTO(@NotNull @Size(max = 255) String authorId, @Size(max = 255) String name, Set<BookDTO> authorBooks) {
        this.authorId = authorId;
        this.name = name;
        this.authorBooks = authorBooks;
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
            "authorId='" + authorId + '\'' +
            ", name='" + name + '\'' +
            ", authorBooks=" + authorBooks +
            '}';
    }
}
