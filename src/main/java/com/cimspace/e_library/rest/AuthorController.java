package com.cimspace.e_library.rest;

import com.cimspace.e_library.model.AuthorDTO;
import com.cimspace.e_library.service.implementation.AuthorServiceImpl;
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
@RequestMapping(value = "/api/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorServiceImpl authorServiceImpl;

    public AuthorController(final AuthorServiceImpl authorServiceImpl) {
        this.authorServiceImpl = authorServiceImpl;
    }

    @GetMapping("/")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorServiceImpl.findAll());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable final String authorId) {
        return ResponseEntity.ok(authorServiceImpl.get(authorId));
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createAuthor(@RequestBody @Valid final AuthorDTO authorDTO) {
        authorServiceImpl.create(authorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{authorId}")
    public ResponseEntity<Void> updateAuthor(@PathVariable final String authorId,
            @RequestBody @Valid final AuthorDTO authorDTO) {
        authorServiceImpl.update(authorId, authorDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable final String authorId) {
        authorServiceImpl.delete(authorId);
        return ResponseEntity.noContent().build();
    }

}
