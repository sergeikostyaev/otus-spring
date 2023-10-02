package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface LibraryService {
    Book getBookById(Long id);

    List<Book> getBooksByName(String name);

    List<Book> getAllBooks();

    void removeBookById(Long id);

    Book saveBook(Book book);

    Comment getCommentById(Long id);

    List<Comment> getCommentsByBookId(Long id);
}
