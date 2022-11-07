package com.aaronr92.weblibrary.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Author {

    @Id
    @GeneratedValue(generator = "authorGen")
    @SequenceGenerator(name = "authorGen",
            sequenceName = "authorSeq",
            allocationSize = 1)
    @ReadOnlyProperty
    private long id;

    private String name;

    private String lastname;

    @JsonProperty("date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy")
    private LocalDate dob;

    @ReadOnlyProperty
    @OneToMany
    @JsonBackReference
    private List<Book> books;

    public void addBook(Book book) {
        books.add(book);
    }
}
