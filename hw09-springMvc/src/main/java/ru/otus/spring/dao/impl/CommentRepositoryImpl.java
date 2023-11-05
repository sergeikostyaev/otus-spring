package ru.otus.spring.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.CommentRepository;
import ru.otus.spring.domain.Comment;

import java.util.List;

import static java.util.Objects.isNull;

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
    public Comment save(Comment comment) {
        if (isNull(comment.getId())) {
            entityManager.persist(comment);
            return comment;
        } else {
            return entityManager.merge(comment);
        }
    }

}
