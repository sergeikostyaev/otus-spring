package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

public interface LibraryService {


    Book insertBook(Book book);

    Book findBookById(Long id);

    void updateBookById(Long id, String newName, Long newAuthorId, Long newGenreId);

    void removeBookById(Long id);

}
