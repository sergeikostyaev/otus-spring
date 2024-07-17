INSERT INTO authors (author_name) VALUES ('Светоний');
INSERT INTO authors (author_name) VALUES ('Достоевский');
INSERT INTO authors (author_name) VALUES ('Булгаков');

INSERT INTO genres (genre_name) VALUES ('Исторические');
INSERT INTO genres (genre_name) VALUES ('Драма');

INSERT INTO books (book_name, genre_id, author_id) VALUES ('Жизнь двенадцати цезарей', 1, 1);
INSERT INTO books (book_name, genre_id, author_id) VALUES ('Белая гвардия', 2, 3);
INSERT INTO books (book_name, genre_id, author_id) VALUES ('Собачье сердце', 2, 3);
INSERT INTO books (book_name, genre_id, author_id) VALUES ('Игрок', 2, 2);

INSERT INTO comments (book_id, comment) VALUES (1, 'Хорошая книга');
INSERT INTO comments (book_id, comment) VALUES (1, 'Отличная книга');