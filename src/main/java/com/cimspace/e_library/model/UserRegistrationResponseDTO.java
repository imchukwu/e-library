package com.cimspace.e_library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponseDTO {


    @NotNull
    @NotEmpty
    @Size(max = 100)
    private Long userId;

    @NotNull
    @NotEmpty
    @Size(max =100 )
    private String fullname;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String username;


}
