package ru.otus.spring.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Genre;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Genre findById(Long id) {
        return entityManager.find(Genre.class, id);
    }

}
