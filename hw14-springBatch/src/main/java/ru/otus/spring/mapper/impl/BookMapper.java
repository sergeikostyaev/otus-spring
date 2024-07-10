package ru.otus.spring.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.mongo.Author;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.Genre;
import ru.otus.spring.mapper.EntityMapper;

@Component
public class BookMapper implements EntityMapper<Book, BookMongo> {
    @Override
    public BookMongo map(Book book) {
        return BookMongo.builder().name(book.getName()).author(Author.builder().name(book.getAuthor().getName()).build())
                .genre(Genre.builder().name(book.getGenre().getName()).build()).build();
    }
}
