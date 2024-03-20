package ru.otus.spring.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.mapper.ModelMapper;

@Component
public class GenreMapper implements ModelMapper<Genre, GenreDto> {
    @Override
    public GenreDto toDto(Genre model) {
        return GenreDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();
    }
}
