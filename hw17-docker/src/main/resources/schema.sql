DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS comments;

CREATE TABLE authors
(
    id          BIGSERIAL PRIMARY KEY,
    author_name VARCHAR(255) NOT NULL
);

CREATE TABLE genres
(
    id         BIGSERIAL PRIMARY KEY,
    genre_name VARCHAR(255) NOT NULL
);

CREATE TABLE books
(
    id        BIGSERIAL PRIMARY KEY,
    book_name VARCHAR(255)                   NOT NULL,
    author_id BIGINT REFERENCES authors (id) NOT NULL,
    genre_id  BIGINT REFERENCES genres (id)  NOT NULL
);

CREATE TABLE comments
(
    id      BIGSERIAL PRIMARY KEY,
    book_id BIGINT REFERENCES books (id) NOT NULL,
    comment TEXT
);



