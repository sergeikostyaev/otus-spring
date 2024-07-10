package ru.otus.spring.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.mongo.Author;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.Comment;
import ru.otus.spring.domain.mongo.Genre;
import ru.otus.spring.mapper.EntityMapper;

import java.util.stream.Collectors;

@Component
public class BookMapper implements EntityMapper<Book, BookMongo> {
    @Override
    public BookMongo map(Book book) {
        return new BookMongo(book.getName(), new Author(book.getAuthor().getName()),
                new Genre(book.getGenre().getName()), book.getComments().stream().map(c -> new Comment(c.getComment())).collect(Collectors.toList()));
    }
}
