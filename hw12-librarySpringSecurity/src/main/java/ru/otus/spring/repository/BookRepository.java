package ru.otus.spring.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.otus.spring.domain.Book;

public interface BookRepository extends R2dbcRepository<Book, Long> {

}
