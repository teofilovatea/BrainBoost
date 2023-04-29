CREATE TABLE favourites_list
(
    id         bigserial primary key,
    status     varchar not null,
    user_id bigserial not null,
    foreign key (user_id) references users(id)
);