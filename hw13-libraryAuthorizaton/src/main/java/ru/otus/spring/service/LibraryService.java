package ru.otus.spring.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;

public interface LibraryService {
    Mono<BookDto> getBookById(Long id);

    Flux<BookDto> getAllBooks();

    void removeBookById(Long id);

    void saveBook(BookDto book);

    Flux<CommentDto> getCommentsByBookId(Long id);

    void saveComment(Comment comment);

    Flux<GenreDto> getAllGenres();

    Flux<AuthorDto> getAllAuthors();
}
