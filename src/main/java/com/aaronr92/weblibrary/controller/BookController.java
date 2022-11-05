package com.aaronr92.weblibrary.controller;

import com.aaronr92.weblibrary.dto.BookDTO;
import com.aaronr92.weblibrary.entity.Book;
import com.aaronr92.weblibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book")
@Slf4j
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<Book> save(@RequestParam("book") BookDTO bookDTO) {
        Book book = service.save(bookDTO);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/v1/book")
                .toUriString());

        return ResponseEntity.created(uri).body(book);
    }

    @PutMapping(path = "/file/{id}")
    public ResponseEntity<Void> saveFile(@PathVariable long id,
                                          @RequestParam MultipartFile file) {
        service.saveFile(id, file);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Book> update(@PathVariable long id,
                                       @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(service.update(id, bookDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
