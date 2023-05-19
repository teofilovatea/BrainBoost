CREATE TABLE questions
(
    id             bigserial primary key,
    text           VARCHAR(255) NOT NULL,
    correct_answer INT NOT NULL,
    quiz_id        INT,
    FOREIGN KEY (quiz_id) REFERENCES quizzes (id)
);