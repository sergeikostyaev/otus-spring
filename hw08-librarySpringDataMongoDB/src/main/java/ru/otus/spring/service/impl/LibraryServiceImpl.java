package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.mapper.ModelMapper;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.service.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookRepository bookRepository;

    private final ModelMapper<Book, BookDto> bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByName(String name) {
        List<Book> books = bookRepository.findByName(name);
        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {

        List<BookDto> books = new ArrayList<>();

        bookRepository.findAll().forEach(book -> {
            books.add(bookMapper.toDto(book));
        });

        return books;
    }

    @Override
    public void removeBookByName(String name) {
        bookRepository.deleteBookByName(name);
    }

    @Override
    public BookDto saveBook(Book book) {
        return bookMapper.toDto(bookRepository.save(book));
    }

}
