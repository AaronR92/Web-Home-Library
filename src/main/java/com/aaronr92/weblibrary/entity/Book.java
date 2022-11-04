package com.aaronr92.weblibrary.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private String name;
    private Author author;
    private String description;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
}
