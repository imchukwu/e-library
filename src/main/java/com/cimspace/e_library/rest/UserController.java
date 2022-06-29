package com.cimspace.e_library.rest;

import com.cimspace.e_library.model.UserRegistrationDTO;
import com.cimspace.e_library.service.implementation.UserServiceImpl;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(final UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserRegistrationDTO>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRegistrationDTO> getUser(@PathVariable final Long userId) {
        return ResponseEntity.ok(userServiceImpl.get(userId));
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createUser(@RequestBody @Valid final UserRegistrationDTO userRegistrationDTO) {
        userServiceImpl.create(userRegistrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable final Long userId,
            @RequestBody @Valid final UserRegistrationDTO userRegistrationDTO) {
        userServiceImpl.update(userId, userRegistrationDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long userId) {
        userServiceImpl.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
