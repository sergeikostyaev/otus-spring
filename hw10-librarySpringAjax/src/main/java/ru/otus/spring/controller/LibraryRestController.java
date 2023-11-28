package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.LibraryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibraryRestController {

    private final LibraryService libraryService;

    @GetMapping("/api/books")
    public List<BookDto> getAllPersons() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return libraryService.getBookById(id);
    }

    @GetMapping("/api/genres")
    public List<GenreDto> getAllGenres() {
        return libraryService.getAllGenres();
    }

    @GetMapping("/api/authors")
    public List<AuthorDto> getAllAuthors() {
        return libraryService.getAllAuthors();
    }

    @GetMapping("/api/comments/{id}")
    public List<CommentDto> getAllCommentsByBookId(@PathVariable Long id) {
        return libraryService.getCommentsByBookId(id);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryService.removeBookById(id);
    }

    @PostMapping("/api/books")
    public BookDto addBook(@RequestBody Book book) {
        return libraryService.saveBook(book);
    }

    @PostMapping("/api/books/comment")
    public CommentDto addBook(@RequestBody Comment comment) {
        return libraryService.saveComment(comment);
    }

}
