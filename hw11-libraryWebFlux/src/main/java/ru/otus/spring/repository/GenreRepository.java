package ru.otus.spring.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.otus.spring.domain.Genre;

public interface GenreRepository extends R2dbcRepository<Genre, Long> {

}
