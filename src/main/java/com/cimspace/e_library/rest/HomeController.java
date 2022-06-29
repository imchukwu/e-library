package com.cimspace.e_library.rest;

import com.cimspace.e_library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/nesera/library")
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(value = {"/", "/index"})
    @ResponseBody
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", bookService.getHomeBooks());
        modelAndView.getModel();
        modelAndView.setViewName("/index");
        return modelAndView;
    }

    @GetMapping("/books/details")
    @ResponseBody
    public ModelAndView book() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("book-details");
        return modelAndView;
    }


}
