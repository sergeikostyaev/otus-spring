package ru.otus.spring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.LibraryService;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Book insertBook(Book book) {
        return bookRepository.addBook(book);
    }

    @Override
    @Transactional
    public Book findBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    @Override
    @Transactional
    public void updateBookById(Long id, String name, Long authorId, Long genreId) {
        bookRepository.updateBookById(id, name, authorId, genreId);
    }

    @Override
    @Transactional
    public void removeBookById(Long id) {
        bookRepository.removeBookById(id);
    }
}
