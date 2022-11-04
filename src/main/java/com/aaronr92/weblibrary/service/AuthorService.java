package com.aaronr92.weblibrary.service;

import com.aaronr92.weblibrary.entity.Author;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    List<Author> authorList = new ArrayList<>();

    public Author findAuthor(long authorId) {
        return authorList.get((int) authorId - 1);
    }

    public List<Author> getAll() {
        return authorList;
    }

    public Author save(Author author) {
        authorList.add(author);
        return author;
    }

    public void delete(long authorId) {
        authorList.remove((int) (authorId - 1));
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Deleted successfully");
    }
}
