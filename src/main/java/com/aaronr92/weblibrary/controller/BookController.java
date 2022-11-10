package com.aaronr92.weblibrary.controller;

import com.aaronr92.weblibrary.dto.BookDTO;
import com.aaronr92.weblibrary.entity.Book;
import com.aaronr92.weblibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/book")
public class BookController {

    private final BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> getAll(@RequestParam(required = false) String name) {
        List<Book> books = service.getAll(name);
        return ResponseEntity.ok(books);
    }

    @GetMapping(path = "{bookId}")
    public ResponseEntity<Book> findBook(@PathVariable long bookId) {
        return ResponseEntity.ok(service.findBook(bookId));
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> findBooksByAuthorName(@RequestParam String name) {
        return ResponseEntity.ok(service.findBooksByAuthorName(name));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Book>> findBooksByAuthorId(@PathVariable long authorId) {
        return ResponseEntity.ok(service.findBooksByAuthorId(authorId));
    }

    @GetMapping("/page/{pageN}")
    public ResponseEntity<Page<Book>> findBookPage(@PathVariable Integer pageN) {
        return ResponseEntity.ok(service.findPage(pageN));
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> getFile(@RequestParam String file) {
        Resource resource = service.getFile(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody BookDTO bookDTO) {
        Book book = service.save(bookDTO);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/v1/book")
                .toUriString());

        return ResponseEntity.created(uri).body(book);
    }

    @PutMapping(path = "/{bookId}/file")
    public ResponseEntity<Void> saveFile(@PathVariable long bookId,
                                         @RequestParam MultipartFile file) {
        service.saveFile(bookId, file);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{bookId}")
    public ResponseEntity<Book> update(@PathVariable long bookId,
                                       @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(service.update(bookId, bookDTO));
    }

    @DeleteMapping(path = "{bookId}")
    public ResponseEntity<Void> delete(@PathVariable long bookId) {
        service.delete(bookId);
        return ResponseEntity.noContent().build();
    }
}
