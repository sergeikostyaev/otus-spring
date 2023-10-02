package ru.otus.spring.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.CommentRepository;
import ru.otus.spring.domain.Comment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Comment findById(Long id) {
        return entityManager.find(Comment.class, id);
    }

    @Override
    public List<Comment> findAllByBookId(Long id) {
        TypedQuery<Comment> query = entityManager.createQuery("select s " +
                        "from Comment s " +
                        "where s.book.id = :book_id",
                Comment.class);
        query.setParameter("book_id", id);
        return query.getResultList();
    }
}
