package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;

import java.util.List;

public interface LibraryService {
    BookDto getBookById(Long id);

    List<BookDto> getBooksByName(String name);

    List<BookDto> getAllBooks();

    void removeBookById(Long id);

    BookDto saveBook(Book book);

    CommentDto getCommentById(Long id);

    List<CommentDto> getCommentsByBookId(Long id);

    Comment saveComment(Comment comment);

}
