CREATE TABLE question_answers
(
    question_id INT,
    answer      VARCHAR(255) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(id)
);