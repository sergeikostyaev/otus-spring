insert into authors (`author_name`)
values ('Светоний');
insert into authors (`author_name`)
values ('Достоевский');
insert into authors (`author_name`)
values ('Булгаков');

insert into genres (`genre_name`)
values ('Исторические');
insert into genres (`genre_name`)
values ('Драма');

insert into books (`book_name`, genre_id, author_id)
values ('Жизнь двенадцати цезарей',1,1);
insert into books (`book_name`, genre_id, author_id)
values ('Белая гвардия',2,3);
insert into books (`book_name`, genre_id, author_id)
values ('Собачье сердце',2,3);
insert into books (`book_name`, genre_id, author_id)
values ('Игрок',2,2);
insert into books (`book_name`)
values ('Книга пользователя');
insert into books (`book_name`)
values ('Книга администратора');

insert into comments (book_id, comment)
values (1, 'Хорошая книга');
insert into comments (book_id, comment)
values (1, 'Отличная книга');

insert into users (user_login, user_password, user_role)
values ('user', 'password', 'USER');
insert into users (user_login, user_password, user_role)
values ('admin', 'password', 'ADMIN');