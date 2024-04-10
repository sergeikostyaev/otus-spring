package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final CommentRepository commentRepository;

    private final ModelMapper<Genre, GenreDto> genreMapper;

    private final ModelMapper<Author, AuthorDto> authorMapper;


    @Override
    public Mono<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .flatMap(book -> {

                    Mono<AuthorDto> authorDtoMono = Optional.ofNullable(book.getAuthorId())
                            .map(authorId -> authorRepository.findById(authorId).map(authorMapper::toDto)).orElse(Mono.just(AuthorDto.builder()
                                    .id(null)
                                    .name(null)
                                    .build()));

                    Mono<GenreDto> genreDtoMono = Optional.ofNullable(book.getGenreId())
                            .map(genreId -> genreRepository.findById(genreId).map(genreMapper::toDto)).orElse(Mono.just(GenreDto.builder()
                                    .id(null)
                                    .name(null)
                                    .build()));

                    return Mono.zip(authorDtoMono, genreDtoMono)
                            .map(tuple -> BookDto.builder()
                                    .id(book.getId())
                                    .name(book.getBookName())
                                    .author(tuple.getT1())
                                    .genre(tuple.getT2())
                                    .build());
                });
    }

    @Override
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .flatMap(book -> {

                    Mono<AuthorDto> authorDtoMono = Optional.ofNullable(book.getAuthorId())
                            .map(authorId -> authorRepository.findById(authorId).map(authorMapper::toDto)).orElse(Mono.just(AuthorDto.builder()
                                    .id(null)
                                    .name(null)
                                    .build()));

                    Mono<GenreDto> genreDtoMono = Optional.ofNullable(book.getGenreId())
                            .map(genreId -> genreRepository.findById(genreId).map(genreMapper::toDto)).orElse(Mono.just(GenreDto.builder()
                                    .id(null)
                                    .name(null)
                                    .build()));

            return Mono.zip(authorDtoMono, genreDtoMono)
                    .map(tuple -> BookDto.builder()
                            .id(book.getId())
                            .name(book.getBookName())
                            .author(tuple.getT1())
                            .genre(tuple.getT2())
                            .build());

        });
    }

    @Override
    public void removeBookById(Long id) {
        bookRepository.deleteById(id).subscribe();
    }

    @Override
    public void saveBook(BookDto book) {
        bookRepository.save(new Book(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId())).subscribe();
    }

    @Override
    public Flux<CommentDto> getCommentsByBookId(Long id) {
        return commentRepository.findAll().map(g -> CommentDto.builder()
                .id(g.getId())
                .comment(g.getComment())
                .build());
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment).subscribe();
    }

    @Override
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll().map(g -> GenreDto.builder()
                .id(g.getId())
                .name(g.getGenreName())
                .build());
    }

    @Override
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().map(g -> AuthorDto.builder()
                .id(g.getId())
                .name(g.getAuthorName())
                .build());
    }
}
