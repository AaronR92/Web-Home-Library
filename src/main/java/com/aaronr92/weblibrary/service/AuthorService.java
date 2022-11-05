package com.aaronr92.weblibrary.service;

import com.aaronr92.weblibrary.entity.Author;
import com.aaronr92.weblibrary.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    public Author findAuthor(long authorId) {
        return checkExistence(repository.findById(authorId));
    }

    public List<Author> getAll(String name) {
        if (name == null) {
            return repository.findAll();
        }

        return repository.findAuthorsByNameContains(name.trim());
    }

    public Author save(Author author) {
        if (repository.exists(Example.of(author, ExampleMatcher.matching()
                .withIgnorePaths("id", "books")
                .withMatcher("dob", ignoreCase())
                .withMatcher("name", ignoreCase())
                .withMatcher("lastname", ignoreCase()))))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This author already exists");

        author.setName(author.getName().trim());
        author.setLastname(author.getLastname().trim());
        return repository.save(author);
    }

    public void delete(long authorId) {
            checkExistence(repository.findById(authorId));
        repository.deleteById(authorId);
    }

    private <T> T checkExistence(Optional<T> optional) {
        if (optional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    Author.class.getSimpleName() + " does not exist");

        return optional.get();
    }
}
