package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

public interface LibraryService {


    void insertBook(String bookName, Long authorId, Long genreId);

    Book findBookById(Long id);

    void updateBookById(Long id, String newName, Long newAuthorId, Long newGenreId);

    void removeBookById(Long id);

}
