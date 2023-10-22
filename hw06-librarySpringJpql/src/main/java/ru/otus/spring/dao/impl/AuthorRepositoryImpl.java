package ru.otus.spring.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.AuthorRepository;
import ru.otus.spring.domain.Author;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public Author getById(Long id){
        return entityManager.find(Author.class, id);
    }

}
