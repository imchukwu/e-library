package com.cimspace.e_library.entity;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class BookDTO {

    @NotNull
    @Size(max = 255)
    private String bookId;

    @Size(max = 255)
    private String isbn;

    @Size(max = 255)
    private String title;

    private String extension;

    private String bookuri;

    private String description;

    private LocalDate dateOfPublication;

    @Size(max = 255)
    private CategoryDTO categoryBooks;

    private Set<AuthorDTO> bookAuthors;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(final String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDate getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(final LocalDate dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public CategoryDTO getCategoryBooks() {
        return categoryBooks;
    }

    public void setCategoryBooks(final CategoryDTO categoryBooks) {
        this.categoryBooks = categoryBooks;
    }

    public Set<AuthorDTO> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(final Set<AuthorDTO> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public String getExtension() { return extension;}

    public void setExtension(String extension) { this.extension = extension;}

    public String getBookuri() {return bookuri;}

    public void setBookuri(String bookuri) {this.bookuri = bookuri;}

}
