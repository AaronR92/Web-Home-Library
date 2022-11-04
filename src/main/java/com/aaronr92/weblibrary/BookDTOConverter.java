package com.aaronr92.weblibrary;

import com.aaronr92.weblibrary.dto.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDTOConverter implements Converter<String, BookDTO> {

    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public BookDTO convert(String source) {
        return mapper.readValue(source, BookDTO.class);
    }
}
