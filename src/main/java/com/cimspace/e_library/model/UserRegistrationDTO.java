package com.cimspace.e_library.model;

import com.cimspace.e_library.utilites.validations.PasswordMatches;
import com.cimspace.e_library.utilites.validations.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserRegistrationDTO implements Serializable {


    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String firstname;

    @NotNull
    @NotEmpty
    @Size(max =100 )
    private String lastname;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String username;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String dob;

    @ValidEmail
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 100)
    private String password;

    @NotNull
    @Size(max = 100)
    private String confirmPassword;


    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
