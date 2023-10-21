package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.service.LibraryService;
import ru.otus.spring.service.IoService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final LibraryService libraryService;

    private final IoService ioService;

    @ShellMethod(key = {"getBook", "gb"})
    public void getBookById(long id) {
        BookDto book;
        book = libraryService.getBookById(id);
        if (nonNull(book)) {
            ioService.print(String.format("Id %s:\nName: %s\nAuthor: %s\nComments:", book.getId(), book.getName(), book.getAuthor().getName()));
            book.getComments().forEach(c -> ioService.print(c.getComment()));
        } else {
            ioService.print(String.format("Book with id '%s' not found%n", id));
        }
    }

    @ShellMethod(key = {"getBook", "gbn"})
    public void getBookByName(String name) {
        List<BookDto> books;
        books = libraryService.getBooksByName(name);
        if (nonNull(books) && !books.isEmpty()) {
            books.forEach(book -> {
                ioService.print(String.format("Id %s:\nName: %s\nAuthor: %s\nComments:", book.getId(), book.getName(), book.getAuthor().getName()));
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
            books.forEach(book -> {
                ioService.print(String.format("Id %s:\nName: %s\nAuthor: %s", book.getId(), book.getName(), book.getAuthor().getName()));
            });
        } else {
            ioService.print("No books found");
        }
    }

    @ShellMethod(key = {"removeBookById", "rb"})
    public void removeBookById(Long id) {
        libraryService.removeBookById(id);
        ioService.print(String.format("Book with id '%s' deleted", id));
    }

    @ShellMethod(key = {"createBook", "cb"})
    public void createBook(String name, Long authorId, Long genreId) {
        BookDto book = libraryService.saveBook(new Book(null, name, Author.builder().id(authorId).build(),
                Genre.builder().id(genreId).build(), null));
        if (isNull(book)) {
            ioService.print(String.format("Book with name '%s' is not created", name));
        } else {
            ioService.print(String.format("Book with name '%s' is created", name));
        }
    }

    @ShellMethod(key = {"updateBook", "ub"})
    public void updateBook(long id, String name, Long authorId, Long genreId) {
        BookDto book = libraryService.saveBook(new Book(id, name, Author.builder().id(authorId).build(),
                Genre.builder().id(genreId).build(), null));

        if (isNull(book)) {
            ioService.print(String.format("Book with id '%s' is not updated", name));
        } else {
            ioService.print(String.format("Book with id '%s' is updated", name));
        }
    }

    @ShellMethod(key = {"getCommentById", "gc"})
    public void getCommentById(long id) {
        CommentDto comment = libraryService.getCommentById(id);
        if (isNull(comment)) {
            ioService.print(String.format("Comment with id '%s' not found", id));
        } else {
            ioService.print(String.format("Comment with id '%s': %s", id, comment.getComment()));
        }
    }

    @ShellMethod(key = {"getCommentsByBookId", "gbc"})
    public void getCommentsByBookId(long id) {
        List<CommentDto> comments = libraryService.getCommentsByBookId(id);
        if (isNull(comments)) {
            ioService.print(String.format("Comments with id '%s' not found", id));
        } else {
            ioService.print(String.format("Comments with id '%s': %s", id, comments));
        }
    }

}
