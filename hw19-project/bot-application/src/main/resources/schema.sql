DROP TABLE IF EXISTS purchases;
create table purchases
(
    id               bigint auto_increment,
    wildberries_id   varchar(255),
    yandex_id        varchar(255),
    ozon_id          varchar(255),
    website_id       varchar(255),
    marketplace_code varchar(255),
    name             varchar(255),
    date             varchar(255),
    region           varchar(255)
);

DROP TABLE IF EXISTS users;
create table users
(
    id            bigint auto_increment,
    user_login    varchar(255) unique,
    user_password varchar(255),
    user_role     varchar(255)
);



