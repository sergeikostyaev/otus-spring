package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.LibraryRepository;
import ru.otus.spring.dao.impl.LibraryRepositoryImpl;
import ru.otus.spring.domain.Book;

import java.util.Collections;
import java.util.Map;

public interface LibraryService {


    void insertBook(String bookName, Long authorId, Long genreId);

    Book findBookById(Long id);

    void updateBookById(Long id, String newName, Long newAuthorId, Long newGenreId);

    void removeBookById(Long id);

}
