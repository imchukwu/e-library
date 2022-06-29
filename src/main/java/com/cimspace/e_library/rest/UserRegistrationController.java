package com.cimspace.e_library.rest;

import com.cimspace.e_library.exception.UserAlreadyExistException;
import com.cimspace.e_library.model.UserRegistrationDTO;
import com.cimspace.e_library.model.UserRegistrationResponseDTO;
import com.cimspace.e_library.service.implementation.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/nesera/library")
public class UserRegistrationController {

    private final UserServiceImpl userServiceImpl;

    public UserRegistrationController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/register-user")
    public String showRegistrationForm(WebRequest webRequest, Model model){
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        model.addAttribute("registrationDTO", registrationDTO);
        return "/register";
    }

    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDTO registrationDTO,
                                            HttpServletRequest request, Errors errors) throws UserAlreadyExistException{
        ModelAndView mav = new ModelAndView();
        UserRegistrationResponseDTO responseDTO  = userServiceImpl.registerUserAccount(registrationDTO);
        return new ModelAndView("Successful Registration", "User", responseDTO);
    }
}
