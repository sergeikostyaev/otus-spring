package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.User;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.repository.UserRepository;
import ru.otus.spring.service.LibraryService;

@RestController
@RequiredArgsConstructor
public class LibraryRestController {

    private final LibraryService libraryService;

    @GetMapping("/api/books")
    @PostFilter("(filterObject.name != 'Книга пользователя' and hasRole('ADMIN') or (filterObject.name != 'Книга администратора' and hasRole('USER')))")
    public Flux<BookDto> getAllBooks() {
        return libraryService.getAllBooks().sort((book1, book2) -> Math.toIntExact(book1.getId().longValue() - book2.getId().longValue()));
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

    @PostMapping("/api/comments")
    public void addComment(@RequestBody Comment comment) {
        libraryService.saveComment(comment);
    }

}
