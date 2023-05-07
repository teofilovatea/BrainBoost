CREATE TABLE lessons
(
    id        bigserial primary key,
    name      text,
    course_id bigint references courses (id) not null,
    content bytea
);