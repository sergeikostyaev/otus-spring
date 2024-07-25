package ru.otus.spring.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.mapper.ModelMapper;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final CommentRepository commentRepository;

    private final ModelMapper<Book, BookDto> bookMapper;

    private final ModelMapper<Comment, CommentDto> commentMapper;

    private final ModelMapper<Genre, GenreDto> genreMapper;

    private final ModelMapper<Author, AuthorDto> authorMapper;


    @Override
    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "bookFallback")
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).get();
        return bookMapper.toDto(book);
    }

    @Override
    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "bookListFallback")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = "bookBreaker")
    public void removeBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "bookFallback")
    public BookDto saveBook(Book book) {
        try {
            authorRepository.findById(book.getAuthor().getId()).get();
            genreRepository.findById(book.getGenre().getId()).get();
        } catch (Exception e) {
            throw new RuntimeException("Irrelevant book or genre id");
        }
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "commentListFallback")
    public List<CommentDto> getCommentsByBookId(Long id) {
        List<Comment> comments = commentRepository.findByBookId(id);
        return comments.stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "commentFallback")
    public CommentDto saveComment(Comment comment) {
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    @CircuitBreaker(name = "genreBreaker", fallbackMethod = "genreListFallback")
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name = "authorBreaker", fallbackMethod = "authorListFallback")
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorMapper::toDto).collect(Collectors.toList());
    }

    public BookDto bookFallback(Exception ex) {
        return BookDto.builder()
                .name("UNKNOWN")
                .build();
    }

    public List<BookDto> bookListFallback(Exception ex) {
        return List.of(BookDto.builder()
                .name("UNKNOWN")
                .build());
    }

    public CommentDto commentFallback(Exception ex) {
        return CommentDto.builder()
                .comment("UNKNOWN")
                .build();
    }

    public List<CommentDto> commentListFallback(Exception ex) {
        return List.of(CommentDto.builder()
                .comment("UNKNOWN")
                .build());
    }

    public List<GenreDto> genreListFallback(Exception ex) {
        return List.of(GenreDto.builder()
                .name("UNKNOWN")
                .build());
    }

    public List<AuthorDto> authorListFallback(Exception ex) {
        return List.of(AuthorDto.builder()
                .name("UNKNOWN")
                .build());
    }
}
