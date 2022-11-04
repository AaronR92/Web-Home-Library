package com.aaronr92.weblibrary.repository;

import com.aaronr92.weblibrary.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByNameIgnoreCaseAndLastnameIgnoreCase(String name, String lastname);
}
