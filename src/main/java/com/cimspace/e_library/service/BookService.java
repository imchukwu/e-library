package com.cimspace.e_library.service;

import com.cimspace.e_library.domain.Author;
import com.cimspace.e_library.domain.Book;
import com.cimspace.e_library.domain.Category;
import com.cimspace.e_library.model.AuthorDTO;
import com.cimspace.e_library.model.BookDTO;
import com.cimspace.e_library.model.CategoryDTO;
import com.cimspace.e_library.repos.AuthorRepository;
import com.cimspace.e_library.repos.BookRepository;
import com.cimspace.e_library.repos.CategoryRepository;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookService(final BookRepository bookRepository,
            final CategoryRepository categoryRepository, final AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(book -> mapToDTO(book, new BookDTO()))
                .collect(Collectors.toList());
    }

    public BookDTO get(final String bookId) {
        return bookRepository.findById(bookId)
                .map(book -> mapToDTO(book, new BookDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final BookDTO bookDTO) {
        final Book book = new Book();
        mapToEntity(bookDTO, book);
        return bookRepository.save(book).getBookId();
    }

    public void update(final String bookId, final BookDTO bookDTO) {
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    public void delete(final String bookId) {
        bookRepository.deleteById(bookId);
    }

    private BookDTO mapToDTO(final Book book, final BookDTO bookDTO) {
        bookDTO.setBookId(book.getBookId());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setDateOfPublication(book.getDateOfPublication());
        bookDTO.setCategoryBooks(new CategoryDTO(book.getCategoryBooks().getCategoryId(), book.getCategoryBooks().getName()));
        bookDTO.setBookAuthors(book.getBookAuthors() == null ? null : book.getBookAuthors().stream()
                .map(author -> new AuthorDTO(author.getAuthorId(), author.getName()))
                .collect(Collectors.toSet()));
        return bookDTO;
    }

    private Book mapToEntity(final BookDTO bookDTO, final Book book) {
        book.setBookId(bookDTO.getBookId());
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setDateOfPublication(bookDTO.getDateOfPublication());
        if (bookDTO.getCategoryBooks() != null && (book.getCategoryBooks() == null || !book.getCategoryBooks().getCategoryId().equals(bookDTO.getCategoryBooks()))) {
            final Category categoryBooks = categoryRepository.findById(bookDTO.getCategoryBooks().getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "categoryBooks not found"));
            book.setCategoryBooks(categoryBooks);
        }
        if (bookDTO.getBookAuthors() != null) {
            final List<Author> bookAuthors =
                    authorRepository.findAllById(bookDTO.getBookAuthors().stream()
                            .map(AuthorDTO::getAuthorId)
                            .collect(Collectors.toList()));
            if (bookAuthors.size() != bookDTO.getBookAuthors().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of bookAuthors not found");
            }
            book.setBookAuthors(new HashSet<>(bookAuthors));
        }
        return book;
    }

    public List<BookDTO> getHomeBooks(){
        return findAll().stream().limit(8).collect(Collectors.toList());
    }

}
