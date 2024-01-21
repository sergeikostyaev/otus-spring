drop table if exists post; create table post (id int not null auto_increment, title varchar(255), comment varchar(255), primary key(id));

drop table if exists authors cascade; create table authors(id int not null auto_increment, author_name varchar(255), primary key (id));
drop table if exists genres cascade; create table genres(id int not null auto_increment, genre_name varchar(255), primary key (id));


drop table if exists books cascade; create table books(
                    id int not null auto_increment,
                    book_name varchar(255),
                    author_id int references authors (id),
                    genre_id int references genres (id),
                    primary key (id)
);

drop table if exists comments cascade; create table comments(
                        id int not null auto_increment,
                        book_id int references books (id),
                        comment varchar(255),
                        primary key (id)
);