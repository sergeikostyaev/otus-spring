package ru.otus.spring.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.BookRepositoryd;
import ru.otus.spring.domain.Book;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryd {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Book getById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b",
                Book.class);
        query.setHint(LOAD.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> getByName(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select s " +
                        "from Book s " +
                        "where s.name = :name",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }


    @Override
    public Book save(Book book) {
        if (isNull(book.getId())) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void remove(Long id) {
        Book book = entityManager.find(Book.class, id);

        if (nonNull(book)) {
            entityManager.remove(book);
        }
    }

}
