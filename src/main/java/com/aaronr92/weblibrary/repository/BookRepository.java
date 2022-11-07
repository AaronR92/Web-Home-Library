package com.aaronr92.weblibrary.repository;

import com.aaronr92.weblibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.author.name LIKE ?1")
    List<Book> findBooksByAuthorName(String name);
}
