package com.cimspace.e_library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "\"user\"")
public class User extends AbstractEntity {

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String username;
    @Column
    private String email;

    @Column
    private String password;

    @OneToMany
    @Column
    private List<Role> userRole = new ArrayList<>();


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<Role> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<Role> userRole) {
        this.userRole = userRole;
    }
}
