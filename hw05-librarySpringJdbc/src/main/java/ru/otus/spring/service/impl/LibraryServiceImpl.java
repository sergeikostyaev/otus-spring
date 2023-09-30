package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.LibraryRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.LibraryService;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;

    @Override
    public void insertBook(String bookName, Long authorId, Long genreId) {
        libraryRepository.insertBook(bookName, authorId, genreId);
    }

    @Override
    public Book findBookById(Long id) {
        return libraryRepository.findBookById(id);
    }

    @Override
    public void updateBookById(Long id, String name, Long authorId, Long genreId) {
        libraryRepository.updateBookById(id, name, authorId, genreId);
    }

    @Override
    public void removeBookById(Long id) {
        libraryRepository.removeBookById(id);
    }
}
