DROP TABLE IF EXISTS authors;
create table authors(
    id bigint auto_increment,
    author_name varchar(8000),
    primary key (id)
);

DROP TABLE IF EXISTS genres;
create table genres(
    id bigint auto_increment,
    genre_name varchar(255),
    primary key (id)
);

DROP TABLE IF EXISTS books;
create table books(
    id bigint auto_increment,
    book_name varchar(255),
    author_id bigint references authors (id),
    genre_id bigint references genres (id),
    primary key (id)
);

DROP TABLE IF EXISTS comments;
create table comments(
    id bigint auto_increment,
    book_id bigint references books (id) on delete cascade,
    comment varchar(255),
    primary key (id)
);

DROP TABLE IF EXISTS users;
create table users
(
    id      bigint auto_increment,
    user_login varchar(255) unique,
    user_password varchar(255),
    user_role varchar(255)
);




