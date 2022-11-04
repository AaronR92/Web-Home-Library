package com.aaronr92.weblibrary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class BookDTO {

    private String name;

    private String author;

    private String description;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;
}
