package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.AuthorRepository;
import ru.otus.spring.dao.BookRepositoryd;
import ru.otus.spring.dao.CommentRepository;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.mapper.ModelMapper;
import ru.otus.spring.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookRepositoryd bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final CommentRepository commentRepository;

    private final ModelMapper<Book, BookDto> bookMapper;

    private final ModelMapper<Comment, CommentDto> commentMapper;

    @Override
    @Transactional
    public BookDto getBookById(Long id) {
        Book book = bookRepository.getById(id);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public List<BookDto> getBooksByName(String name) {
        List<Book> books = bookRepository.getByName(name);
        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<BookDto> getAllBooks() {
        return bookRepository.getAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeBookById(Long id) {
        bookRepository.remove(id);
    }

    @Override
    @Transactional
    public BookDto saveBook(Book book) {
        Author author = authorRepository.getById(book.getAuthor().getId());
        Genre genre = genreRepository.findById(book.getGenre().getId());

        if (isNull(genre) || isNull(author)) {
            throw new RuntimeException("Invalid genre or author");
        }
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public CommentDto getCommentById(Long id){
        return commentMapper.toDto(commentRepository.findById(id));
    }

    @Override
    @Transactional
    public List<CommentDto> getCommentsByBookId(Long id){
        Book book =  bookRepository.getById(id);
        return bookMapper.toDto(book).getComments();
    }
}
