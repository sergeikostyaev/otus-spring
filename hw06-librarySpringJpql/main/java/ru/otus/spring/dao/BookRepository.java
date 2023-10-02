package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

public interface BookRepository {

    Book findBookById(Long id);

    Book addBook(Book book);

    void removeBookById(Long id);

    void updateBookById(Long id, String newName, Long newAuthorId, Long newGenreId);
}
