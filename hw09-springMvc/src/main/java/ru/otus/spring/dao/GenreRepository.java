package ru.otus.spring.dao;
import ru.otus.spring.domain.Genre;

public interface GenreRepository {

    Genre findById(Long id);

}
