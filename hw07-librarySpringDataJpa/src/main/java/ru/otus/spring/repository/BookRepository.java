package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);

    List<Book> findByAuthorAndGenre(Author author, Genre genre);

}
