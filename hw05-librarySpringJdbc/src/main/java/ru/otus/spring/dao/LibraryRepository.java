package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

public interface LibraryRepository {

    Book findBookById(Long id);

    void insertBook(String bookName, Long author_id, Long genre_id);

    void removeBookById(Long id);

    void updateBookById(Long id, String newName, Long newAuthorId, Long newGenreId);
}
