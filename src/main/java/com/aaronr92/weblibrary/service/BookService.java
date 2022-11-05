package com.aaronr92.weblibrary.service;

import com.aaronr92.weblibrary.dto.BookDTO;
import com.aaronr92.weblibrary.entity.Author;
import com.aaronr92.weblibrary.entity.Book;
import com.aaronr92.weblibrary.repository.AuthorRepository;
import com.aaronr92.weblibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final List<String> allowedExtensions =
            List.of("pdf", "epub", "fb2", "txt", "fb3", "rtf");

    public Book save(BookDTO bookDTO) {
        String[] authorName = bookDTO.getAuthor().split(" ");
        Author author = authorRepository.findAuthorByNameIgnoreCaseAndLastnameIgnoreCase(
                authorName[0], authorName[1]
        );

        if (author == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This author does not exist");

        Book book = Book.builder()
                .name(bookDTO.getName())
                .author(author)
                .releaseDate(bookDTO.getReleaseDate())
                .description(bookDTO.getDescription())
                .build();

        if (bookRepository.exists(Example.of(book, ExampleMatcher.matching()
                .withIgnorePaths("id", "description", "file")
                .withMatcher("name", ignoreCase())
                .withMatcher("author", ignoreCase())
                .withMatcher("releaseDate", ignoreCase()))))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This book already exists");

        return bookRepository.save(book);
    }

    public void saveFile(long bookId, MultipartFile file) {
        File dir = new File("A:\\Files");

        String fileExtension = file.getOriginalFilename().split("\\.")[1].toLowerCase();

        if (!allowedExtensions.contains(fileExtension))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "This file cannot be processed by the server");

        // Naming by order in store directory
        String filename = (dir.listFiles().length + 1) + "." +
                fileExtension;

        Path path = Paths.get(dir + "\\" + filename);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MultipartFile getFile() {
        return null;
    }

    public Book update(long id, BookDTO bookDTO) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This book is not found");

        Book b = book.get();
        b.setName(bookDTO.getName());
        b.setDescription(bookDTO.getDescription());
        b.setReleaseDate(bookDTO.getReleaseDate());

        String authorName = b.getAuthor().getName() + " " + b.getAuthor().getLastname();
        if (!authorName.equals(bookDTO.getAuthor().trim())) {
            String[] name = bookDTO.getAuthor().trim().split(" ");
            Author author = authorRepository.findAuthorByNameIgnoreCaseAndLastnameIgnoreCase(
                    name[0], name[1]
            );

            if (author == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This author does not exist");
            b.setAuthor(author);
        }

        return bookRepository.save(b);
    }

    public void delete(long id) {
        if (!bookRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This book does not exist");

        bookRepository.deleteById(id);
    }
}
