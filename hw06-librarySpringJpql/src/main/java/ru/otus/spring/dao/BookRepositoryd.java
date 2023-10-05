package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepositoryd {

    Book getById(long id);

    List<Book> getAll();

    List<Book> getByName(String name);

    Book save(Book book);

    void remove(Long id);

}
