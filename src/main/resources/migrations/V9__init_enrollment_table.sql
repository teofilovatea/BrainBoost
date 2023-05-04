CREATE TABLE enrollment
(
    id            bigserial primary key,
    user_id       bigserial not null,
    course_id     bigserial NOT NULL,
    date_enrolled date      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (course_id) REFERENCES courses (id)
);