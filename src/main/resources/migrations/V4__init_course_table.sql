CREATE TABLE courses
(
    id         bigserial primary key,
    name       varchar(200) not null,
    category   varchar(200) not null,
    description text not null,
    teacher varchar(300) not null,
    image text not null,
    time varchar(100) not null,
    category_id bigserial not null,
    foreign key (category_id) references categories(id)
);