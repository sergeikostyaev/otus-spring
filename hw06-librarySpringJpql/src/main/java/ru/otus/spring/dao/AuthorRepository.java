package ru.otus.spring.dao;
import ru.otus.spring.domain.Author;

public interface AuthorRepository {

    Author getById(Long id);

}
