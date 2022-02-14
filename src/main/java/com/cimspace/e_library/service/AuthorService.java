package com.cimspace.e_library.service;

import com.cimspace.e_library.model.AuthorDTO;
import com.cimspace.e_library.domain.Author;
import com.cimspace.e_library.repos.AuthorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(author -> mapToDTO(author, new AuthorDTO()))
                .collect(Collectors.toList());
    }

    public AuthorDTO get(final String authorId) {
        return authorRepository.findById(authorId)
                .map(author -> mapToDTO(author, new AuthorDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final AuthorDTO authorDTO) {
        final Author author = new Author();
        mapToEntity(authorDTO, author);
        return authorRepository.save(author).getAuthorId();
    }

    public void update(final String authorId, final AuthorDTO authorDTO) {
        final Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(authorDTO, author);
        authorRepository.save(author);
    }

    public void delete(final String authorId) {
        authorRepository.deleteById(authorId);
    }

    private AuthorDTO mapToDTO(final Author author, final AuthorDTO authorDTO) {
        authorDTO.setAuthorId(author.getAuthorId());
        authorDTO.setName(author.getName());
        return authorDTO;
    }

    private Author mapToEntity(final AuthorDTO authorDTO, final Author author) {
        author.setAuthorId(authorDTO.getAuthorId());
        author.setName(authorDTO.getName());
        return author;
    }

}
