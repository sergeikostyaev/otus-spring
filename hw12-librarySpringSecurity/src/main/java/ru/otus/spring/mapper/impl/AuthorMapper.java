package ru.otus.spring.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.mapper.ModelMapper;

@Component
public class AuthorMapper implements ModelMapper<Author, AuthorDto> {
    @Override
    public AuthorDto toDto(Author model) {
        return AuthorDto.builder()
                .id(model.getId())
                .name(model.getAuthorName())
                .build();
    }
}
