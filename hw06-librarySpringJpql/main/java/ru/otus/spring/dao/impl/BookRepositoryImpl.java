package ru.otus.spring.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Book;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.nonNull;


@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Book addBook(Book book) {
        return entityManager.merge(book);
    }

    @Override
    public Book findBookById(Long id) {
        var a =  entityManager.find(Book.class, id);
        System.out.println(a.getComments());
        return a;
    }

    @Override
    public void updateBookById(Long id, String newName, Long newAuthorId, Long newGenreId) {
        //namedParameterJdbcOperations.update("update books set book_name = :book_name, author_id = :author_id, genre_id = :genre_id where id = :id",
//                Map.of("id", id,"book_name", newName, "author_id", newAuthorId, "genre_id", newGenreId));
    }

    @Override
    public void removeBookById(Long id) {
        Book book = entityManager.find(Book.class, id);
        if(nonNull(book)){
            entityManager.remove(book);
        }
    }

}
