package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class BookDto {

    private Long id;

    private String name;

    private AuthorDto author;

    private GenreDto genre;

    private List<CommentDto> comments;
}
