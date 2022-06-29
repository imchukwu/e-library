package com.cimspace.e_library.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nesera/library")
public class LoginController {

    @GetMapping("/login-user")
    public String displayContactUs(){

        return "/login";
    }
}
