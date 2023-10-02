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
values ('ABC',2,2);

insert into comments (book_id, comment)
values (1, 'Хорошая книга');
insert into comments (book_id, comment)
values (1, 'Отличная книга');