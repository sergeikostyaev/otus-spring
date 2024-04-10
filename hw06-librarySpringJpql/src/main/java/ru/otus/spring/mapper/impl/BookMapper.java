package ru.otus.spring.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.mapper.ModelMapper;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class BookMapper implements ModelMapper<Book, BookDto> {
    @Override
    public BookDto toDto(Book model) {
        return isNull(model) ? null : BookDto.builder()
                .id(model.getId())
                .name(model.getName())
                .genre(GenreDto.builder()
                        .id(model.getGenre().getId())
                        .name(model.getGenre().getName())
                        .build())
                .author(AuthorDto.builder()
                        .id(model.getAuthor().getId())
                        .name(model.getAuthor().getName())
                        .build())
                .comments(isNull(model.getComments())? null : model.getComments().stream().map(m -> CommentDto.builder()
                        .id(m.getId())
                        .comment(m.getComment())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
