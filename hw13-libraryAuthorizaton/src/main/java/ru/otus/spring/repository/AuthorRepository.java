package ru.otus.spring.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.otus.spring.domain.Author;

public interface AuthorRepository extends R2dbcRepository<Author, Long> {

}
