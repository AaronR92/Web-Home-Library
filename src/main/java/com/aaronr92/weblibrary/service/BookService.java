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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book save(BookDTO bookDTO, MultipartFile file) {
        String[] authorName = bookDTO.getAuthor().split(" ");
        Author author = authorRepository.findAuthorByNameIgnoreCaseAndLastnameIgnoreCase(
                authorName[0], authorName[1]
        );

        if (author == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This book already exists");

        book.setFile(saveFile(file, bookDTO.getName()));

        return bookRepository.save(book);
    }

    public String saveFile(MultipartFile file, String filename) {
        File dir = new File("A:\\Files");
//        String filename = (dir.listFiles().length + 1) + "." +
//                (file.getOriginalFilename().split("\\.")[1]);
        Path path = Paths.get(dir + "\\" + filename + "." +
                file.getOriginalFilename().split("\\.")[1]);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path.toString();
    }
}
