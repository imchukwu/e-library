package com.cimspace.e_library.rest;

import com.cimspace.e_library.model.BookDTO;
import com.cimspace.e_library.service.BookService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseBody
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", bookService.findAll());
        modelAndView.getModel();
        modelAndView.setViewName("/books");
        return modelAndView;
    }

    @GetMapping("/{bookId}")
    public ModelAndView getBook(@PathVariable final String bookId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("book", bookService.get(bookId));
        modelAndView.getModel();
        modelAndView.setViewName("book-details");
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid final BookDTO bookDTO) {
        bookService.create(bookDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(@PathVariable final String bookId,
            @RequestBody @Valid final BookDTO bookDTO) {
        bookService.update(bookId, bookDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable final String bookId) {
        bookService.delete(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/books")
    public String listBooks(@RequestParam("page") Optional<Integer> page) {
        return "books.html";
    }

}
