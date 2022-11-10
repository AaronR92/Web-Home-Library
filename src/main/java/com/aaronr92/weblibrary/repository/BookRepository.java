package com.aaronr92.weblibrary.repository;

import com.aaronr92.weblibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book INNER JOIN author a ON book.author_id = a.id WHERE a.name LIKE CONCAT(?1, '%')",
            nativeQuery = true)
    List<Book> findBooksByAuthorName(String name);

    @Query(value = "SELECT * FROM book INNER JOIN author a ON book.author_id = a.id WHERE a.id = ?1",
            nativeQuery = true)
    List<Book> findBooksByAuthorId(long id);

    List<Book> findBooksByNameContains(String name);
}
