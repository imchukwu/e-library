package com.cimspace.e_library.service;

import com.cimspace.e_library.exception.UserAlreadyExistException;
import com.cimspace.e_library.model.UserRegistrationDTO;
import com.cimspace.e_library.model.UserRegistrationResponseDTO;

public interface UserService {

    UserRegistrationResponseDTO registerUserAccount(UserRegistrationDTO registrationDTO) throws UserAlreadyExistException;
}
