CREATE TABLE quizzes
(
    id        bigserial primary key,
    name      VARCHAR(255) NOT NULL,
    course_id INT,
    user_id INT,
    FOREIGN KEY (course_id) REFERENCES courses (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);