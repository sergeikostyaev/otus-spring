package ru.otus.spring.controller;//package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.LibraryService;

@RestController
@RequiredArgsConstructor
public class LibraryRestController {

    private final LibraryService libraryService;

    @GetMapping("/api/books")
    public Flux<BookDto> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/api/books/{id}")
    public Mono<BookDto> getBookById(@PathVariable Long id) {
        return libraryService.getBookById(id);
    }

    @GetMapping("/api/genres")
    public Flux<GenreDto> getAllGenres() {
        return libraryService.getAllGenres();
    }

    @GetMapping("/api/authors")
    public Flux<AuthorDto> getAllAuthors() {
        return libraryService.getAllAuthors();
    }

    @GetMapping("/api/comments/{id}")
    public Flux<CommentDto> getAllCommentsByBookId(@PathVariable Long id) {
        return libraryService.getCommentsByBookId(id);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryService.removeBookById(id);
    }

    @PostMapping("/api/books")
    public void addBook(@RequestBody BookDto book) {
        libraryService.saveBook(book);
    }

//    @PostMapping("/api/comment")
//    public CommentDto addComment(@RequestBody Comment comment) {
//        return libraryService.saveComment(comment);
//    }

}
