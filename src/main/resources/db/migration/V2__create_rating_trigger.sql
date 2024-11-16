CREATE OR REPLACE FUNCTION update_book_rating()
    RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE book
        SET rating = (SELECT COALESCE(AVG(rating), 0.0)
                      FROM comment
                      WHERE book_id = NEW.book_id)
        WHERE book_id = NEW.book_id;
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE book
        SET rating = (SELECT COALESCE(AVG(rating), 0.0)
                      FROM comment
                      WHERE book_id = OLD.book_id)
        WHERE book_id = OLD.book_id;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_book_rating_after_insert
    AFTER INSERT
    ON comment
    FOR EACH ROW
EXECUTE FUNCTION update_book_rating();

CREATE TRIGGER update_book_rating_after_delete
    AFTER DELETE
    ON comment
    FOR EACH ROW
EXECUTE FUNCTION update_book_rating();
