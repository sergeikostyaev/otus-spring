package ru.otus.spring.dto;

import lombok.Builder;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import java.util.List;

@Builder
public class BookDto {

    private Long id;

    private String name;

    private AuthorDto author;

    private GenreDto genre;

    private List<CommentDto> comments;
}
