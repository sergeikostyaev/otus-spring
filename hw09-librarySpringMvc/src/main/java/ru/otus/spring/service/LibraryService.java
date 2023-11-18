package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;

import java.util.List;

public interface LibraryService {
    BookDto getBookById(Long id);

    List<BookDto> getAllBooks();

    void removeBookById(Long id);

    BookDto saveBook(Book book);

    List<CommentDto> getCommentsByBookId(Long id);

    Comment saveComment(Comment comment);

    List<GenreDto> getAllGenres();

    List<AuthorDto> getAllAuthors();
}
