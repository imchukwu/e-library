package com.cimspace.e_library.mapper.UserMapper;

import com.cimspace.e_library.domain.User;
import com.cimspace.e_library.model.UserRegistrationResponseDTO;

public class UserMapper {

    private UserMapper(){

    }
    public static UserRegistrationResponseDTO toUserRespond(User save) {
        UserRegistrationResponseDTO user = new UserRegistrationResponseDTO();
        user.setUserId(save.getId());
        user.setFullname(save.getFirstname()+ " " + save.getLastname());
        user.setUsername(save.getUsername());
        return user;

    }
}
