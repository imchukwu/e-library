package com.cimspace.e_library.rest;

import com.cimspace.e_library.model.BookDTO;
import com.cimspace.e_library.service.BookService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

//    @GetMapping
//    public ResponseEntity<List<BookDTO>> getAllBooks() {
//        return ResponseEntity.ok(bookService.findAll());
//    }

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

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String listBooks(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);

//        Page<BookDTO> bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

//        model.addAttribute("bookPage", bookPage);
//
//        int totalPages = bookPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }

        return "books.html";
    }

//    @GetMapping("/{bookId}")
//    @ResponseBody
//    public ModelAndView bookDetails(@PathVariable final String bookId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("book", bookService.get(bookId));
//        modelAndView.getModel();
//        modelAndView.setViewName("book-details");
//        return modelAndView;
//    }

}
