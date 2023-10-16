package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;

import java.util.List;

public interface LibraryService {
    BookDto getBookById(Long id);

    List<BookDto> getBooksByName(String name);

    List<Book> getAllBooks();

    void removeBookById(Long id);

    Book saveBook(Book book);

    Comment getCommentById(Long id);

    List<Comment> getCommentsByBookId(Long id);
}
