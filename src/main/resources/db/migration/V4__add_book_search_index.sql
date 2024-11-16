CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_book_name_trgm ON book USING gin(LOWER(book_name) gin_trgm_ops);

CREATE INDEX idx_author_name_trgm ON author USING gin(LOWER(author_name) gin_trgm_ops);

CREATE INDEX idx_genre_name_trgm ON genre USING gin(LOWER(genre_name) gin_trgm_ops);
