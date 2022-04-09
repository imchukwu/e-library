package com.cimspace.e_library.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nesera/books")
public class BooksController {


  @GetMapping(value = {"/", "index"})
  public String books(){
    return "books";
  }
}
