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

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).get();
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByName(String name) {
        List<Book> books = bookRepository.findByName(name);
        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void removeBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
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
    public CommentDto getCommentById(Long id) {

        Comment comment;

        try {
            comment = commentRepository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Irrelevant comment id");
        }

        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByBookId(Long id) {
        Book book = bookRepository.findById(id).get();
        return bookMapper.toDto(book).getComments();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByAuthorAndGenre(Long authorId, Long genreId) {
        List<Book> books = bookRepository.findByAuthorAndGenre(new Author(authorId, null), new Genre(genreId, null));
        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }
}
