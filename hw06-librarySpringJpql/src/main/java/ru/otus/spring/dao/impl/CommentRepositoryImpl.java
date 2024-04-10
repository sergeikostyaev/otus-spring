package ru.otus.spring.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.CommentRepository;
import ru.otus.spring.domain.Comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Comment findById(Long id) {
        return entityManager.find(Comment.class, id);
    }

}
