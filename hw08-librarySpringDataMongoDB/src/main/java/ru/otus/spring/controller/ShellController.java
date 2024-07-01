package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.IoService;
import ru.otus.spring.service.LibraryService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final LibraryService libraryService;

    private final IoService ioService;

    @ShellMethod(key = {"getBook", "gbn"})
    public void getBookByName(String name) {
        List<BookDto> books;
        books = libraryService.getBooksByName(name);
        if (nonNull(books) && !books.isEmpty()) {
            books.forEach(book -> {
                ioService.print(String.format("Id %s:\nName: %s\nAuthor: %s\nGenre: %s\nComments:",
                        book.getId(), book.getName(), book.getGenre().getName(), book.getAuthor().getName()));
                book.getComments().forEach(c -> ioService.print(c.getComment()));
            });
        } else {
            ioService.print(String.format("Book with name '%s' not found%n", name));
        }
    }

    @ShellMethod(key = {"getAllBooks", "gab"})
    public void getAllBooks() {
        var books = libraryService.getAllBooks();
        if (nonNull(books) && !books.isEmpty()) {
            books.forEach(book -> ioService.print(String.format("Id %s:\nName: %s\nAuthor: %s\nGenre: %s\nComments:",
                    book.getId(), book.getName(), book.getGenre().getName(), book.getAuthor().getName())));
        } else {
            ioService.print("No books found");
        }
    }

    @ShellMethod(key = {"removeBookById", "rb"})
    public void removeBookByName(String name) {
        libraryService.removeBookByName(name);
        ioService.print(String.format("Book with name '%s' deleted", name));
    }

    @ShellMethod(key = {"createBook", "cb"})
    public void createBook(String name, String authorName, String genreName) {
        BookDto book = libraryService.saveBook(Book.builder().name(name).author(Author.builder().name(authorName).build())
                .genre(Genre.builder().name(genreName).build()).build());
        if (isNull(book)) {
            ioService.print(String.format("Book with name '%s' is not created", name));
        } else {
            ioService.print(String.format("Book with name '%s' is created", name));
        }
    }

}
