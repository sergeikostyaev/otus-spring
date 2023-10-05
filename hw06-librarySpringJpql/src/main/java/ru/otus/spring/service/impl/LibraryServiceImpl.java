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
import ru.otus.spring.service.LibraryService;

import java.util.List;

import static java.util.Objects.isNull;


@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final BookRepositoryd bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        Book book = bookRepository.getById(id);
        book.getComments().size();
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByName(String name) {
        List<Book> books = bookRepository.getByName(name);
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    @Override
    @Transactional
    public void removeBookById(Long id) {
        bookRepository.remove(id);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        Author author = authorRepository.getById(book.getAuthor().getId());
        Genre genre = genreRepository.findById(book.getGenre().getId());

        if (isNull(genre) || isNull(author)) {
            throw new RuntimeException("Invalid genre or author");
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Comment getCommentById(Long id){
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Comment> getCommentsByBookId(Long id){
        return commentRepository.findAllByBookId(id);
    }

}
