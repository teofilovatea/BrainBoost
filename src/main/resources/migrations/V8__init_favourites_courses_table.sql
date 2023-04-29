CREATE TABLE favourites_list_courses
(
    favourites_list_id bigserial not null,
    foreign key (favourites_list_id) references favourites_list(id),
    courses_id bigserial not null,
    foreign key (courses_id) references courses(id),
    primary key (favourites_list_id, courses_id)
);