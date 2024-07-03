package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;

import java.util.List;

public interface LibraryService {

    List<BookDto> getBooksByName(String name);

    List<BookDto> getAllBooks();

    void removeBookByName(String name);

    BookDto saveBook(Book book);

}
