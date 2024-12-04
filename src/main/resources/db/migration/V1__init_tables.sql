CREATE TABLE author
(
    author_id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    author_name      VARCHAR(256) UNIQUE NOT NULL,
    author_photo_url TEXT
);

CREATE TABLE book
(
    book_id      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    book_name    VARCHAR(256) NOT NULL,
    author_id    BIGINT       NOT NULL REFERENCES author (author_id),
    release_year SMALLINT     NOT NULL,
    age_limit    SMALLINT     NOT NULL,
    description  TEXT         NOT NULL,
    status       VARCHAR(20)  NOT NULL                                 DEFAULT 'AVAILABLE',
    rating       NUMERIC(2, 1) CHECK (rating >= 0.0 AND rating <= 5.0) DEFAULT 0.0,
    photo_url    TEXT         NOT NULL
);

CREATE TABLE genre
(
    genre_id   INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    genre_name VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE book_genre
(
    book_id  BIGINT NOT NULL REFERENCES book (book_id),
    genre_id INT    NOT NULL REFERENCES genre (genre_id),

    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE comment
(
    comment_id UUID PRIMARY KEY,
    user_id    UUID   NOT NULL,
    comment    TEXT   NOT NULL,
    rating     INT    NOT NULL CHECK (rating >= 0 AND rating <= 5),
    book_id    BIGINT NOT NULL REFERENCES book (book_id)
);

CREATE TABLE book_rent_queue
(
    rent_id    UUID PRIMARY KEY,
    book_id    BIGINT        NOT NULL REFERENCES book (book_id),
    user_id    UUID          NOT NULL,
    due_date   DATE          NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE book_reservation_queue
(
    reservation_id UUID PRIMARY KEY,
    book_id        BIGINT UNIQUE NOT NULL REFERENCES book (book_id),
    user_id        UUID          NOT NULL,
    due_date       DATE          NOT NULL,
    deleted_at     TIMESTAMP
);
