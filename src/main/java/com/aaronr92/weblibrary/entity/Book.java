package com.aaronr92.weblibrary.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;

    @ReadOnlyProperty
    @ElementCollection
    private Set<String> files;

    @JsonIgnore
    public void addFile(String filePath) {
        if (files == null) {
            files = new HashSet<>();
        }
        files.add(filePath);
    }
}
