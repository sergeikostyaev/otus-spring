package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.LibraryRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LibraryRepositoryImpl implements LibraryRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insertBook(String bookName, Long authorId, Long genreId) {
        namedParameterJdbcOperations.update("insert into books (book_name, author_id, genre_id)" +
                " values (:book_name, :author_id, :genre_id)", Map.of("book_name", bookName, "author_id", authorId, "genre_id", genreId));

    }
    @Override
    public Book findBookById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select books.id, book_name, author_id, author_name, genre_id, " +
                "genre_name FROM BOOKS inner join authors on books.author_id = " +
                "authors.id inner join genres on books.genre_id = genres.id where books.id = :id", params, new BookMapper());
    }

    @Override
    public void updateBookById(Long id, String newName, Long newAuthorId, Long newGenreId) {
        namedParameterJdbcOperations.update("update books set book_name = :book_name, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of("id", id,"book_name", newName, "author_id", newAuthorId, "genre_id", newGenreId));
    }

    @Override
    public void removeBookById(Long id) {
        namedParameterJdbcOperations.update("delete from books where id = :id", Map.of("id", id));
    }


    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("book_name");
            long authorId = resultSet.getLong("author_id");
            String authorName = resultSet.getString("author_name");
            long genreId = resultSet.getLong("genre_id");
            String genreName = resultSet.getString("genre_name");
            return new Book(id, name, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("genre_name");
            return new Genre(id, name);
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("author_name");
            return new Author(id, name);
        }
    }

}
