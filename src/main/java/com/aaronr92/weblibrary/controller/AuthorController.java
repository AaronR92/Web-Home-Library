package com.aaronr92.weblibrary.controller;

import com.aaronr92.weblibrary.entity.Author;
import com.aaronr92.weblibrary.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/author")
public class AuthorController {
    private final AuthorService service;

    @GetMapping(path = "{authorId}")
    public ResponseEntity<Author> getAuthor(@PathVariable long authorId) {
        return ResponseEntity.ok(service.findAuthor(authorId));
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(service.getAll(name));
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody Author author) {
        author = service.save(author);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/v1/author").toUriString());
        return ResponseEntity.created(uri).body(author);
    }

    @DeleteMapping(path = "{authorId}")
    public ResponseEntity<Void> delete(@PathVariable long authorId) {
        service.delete(authorId);
        return ResponseEntity.noContent().build();
    }
}
