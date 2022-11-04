package com.aaronr92.weblibrary.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue(generator = "bookGen")
    @SequenceGenerator(name = "bookGen",
        sequenceName = "bookSeq",
        allocationSize = 1)
    private long id;

    private String name;

    @ManyToOne
    private Author author;

    private String description;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    private String file;
}
