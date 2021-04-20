INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'Ivan', 'Ivanov', '{noop}password'),
       ('admin@gmail.com', 'Vladimir', 'Putin', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO POLL (DESCRIPTION, START_DATE, END_DATE)
VALUES ('Poll for Teenagers', DATE '2021-01-18', DATE '2021-12-25'),
       ('Poll for Adults', DATE '2021-04-21', DATE '2021-04-25');

INSERT INTO QUESTION (TEXT, POLL_ID)
VALUES ('What is your age?', 2),
       ('Write your age', 1);

INSERT INTO ANSWER (NAME, QUESTION_ID)
VALUES ('from 18 to 25', 1),
       ('from 25 to 35', 1),
       ('from 35 to 45', 1),
       ('from 45 and older', 1);

INSERT INTO USER_ANSWER (ANSWER_ID, QUESTION_ID, USER_ID)
VALUES (1, 1 ,1);