INSERT INTO author (author_name, author_photo_url)
VALUES ('Толстой Лев Николаевич', 'url'),
       ('Лермонтов Михаил Юрьевич', 'url'),
       ('Достоевский Фёдор Михайлович', 'url'),
       ('Булгаков Михаил Афанасьевич', 'url');

INSERT INTO book (book_name, author_id, release_year, age_limit, description, photo_url)
VALUES ('Война и Мир', 1, 2024, 12, 'testtesttest', 'test'),
       ('Кавказский пленник', 1, 2024, 12, 'testtesttest', 'test'),
       ('Анна Каренина', 1, 2024, 12, 'testtesttest', 'test'),
       ('Детство', 1, 2024, 12, 'testtesttest', 'test'),
       ('Отрочество', 1, 2024, 12, 'testtesttest', 'test'),
       ('Юность', 1, 2024, 12, 'testtesttest', 'test'),
       ('После бала', 1, 2024, 12, 'testtesttest', 'test'),
       ('Утро помещика', 1, 2024, 12, 'testtesttest', 'test'),
       ('Поликушка', 1, 2024, 12, 'testtesttest', 'test'),
       ('Два гусара', 1, 2024, 12, 'testtesttest', 'test'),
       ('Декабристы', 1, 2024, 12, 'testtesttest', 'test'),
       ('Герой нашего времени', 2, 2024, 12, 'testtesttest', 'test'),
       ('Мцыри', 2, 2024, 12, 'testtesttest', 'test'),
       ('Демон', 2, 2024, 12, 'testtesttest', 'test'),
       ('Идиот', 3, 2024, 12, 'testtesttest', 'test'),
       ('Преступление и наказание', 3, 2024, 12, 'testtesttest', 'test'),
       ('Братья Карамазовы', 3, 2024, 12, 'testtesttest', 'test'),
       ('Мастер и Маргарита', 4, 2024, 12, 'testtesttest', 'test'),
       ('Морфий', 4, 2024, 12, 'testtesttest', 'test'),
       ('Собачье сердце', 4, 2024, 12, 'testtesttest', 'test'),
       ('Белая гвардия', 4, 2024, 12, 'testtesttest', 'test');

INSERT INTO genre (genre_name)
VALUES ('Классика'), ('Научная фантастика'), ('Фентези'), ('Детектив'),
       ('Ужасы'), ('Историческое'), ('Драма');

INSERT INTO book_genre (book_id, genre_id)
VALUES (1, 1), (1, 6), (1, 7),
       (2, 1), (2, 7),
       (3, 1), (3, 7),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (11, 1),
       (12, 1),
       (13, 1),
       (14, 1),
       (15, 1),
       (16, 1),
       (17, 1),
       (18, 1),
       (19, 1),
       (20, 1), (20, 2),
       (21, 1);
