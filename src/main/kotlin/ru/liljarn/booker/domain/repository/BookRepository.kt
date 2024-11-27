package ru.liljarn.booker.domain.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.dto.Book
import ru.liljarn.booker.domain.model.dto.BookInfo
import ru.liljarn.booker.domain.model.entity.BookEntity
import ru.liljarn.booker.domain.model.type.BookStatus
import java.util.*

@Repository
interface BookRepository : CrudRepository<BookEntity, Long> {

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.status,
            rq.user_id AS rent_user_id, brq.user_id AS reservation_user_id,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genre g ON g.genre_id = bg.genre_id
        LEFT JOIN book_rent_queue rq ON rq.book_id = b.book_id
        LEFT JOIN book_reservation_queue brq ON brq.book_id = b.book_id
        WHERE b.book_id = :id
        GROUP BY b.book_id, a.author_id, rq.user_id, brq.user_id
        ORDER BY b.book_id;
    """)
    fun findByBookId(@Param("id") bookId: Long): BookInfo?

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.status,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genre g ON g.genre_id = bg.genre_id
        WHERE a.author_id = :id
        AND b.status != 'NOT_AVAILABLE'
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findPageByAuthorId(
        @Param("id") authorId: Long,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<Book>

    @Query("""
        SELECT COUNT(*)
        FROM book b
        WHERE b.author_id = :id
        AND b.status != 'NOT_AVAILABLE'
    """)
    fun countByAuthorId(@Param("id") authorId: Long): Long

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.status,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genre g ON g.genre_id = bg.genre_id
        WHERE g.genre_id = :id
        AND b.status != 'NOT_AVAILABLE'
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findPageByGenreId(
        @Param("id") genreId: Long,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<Book>

    @Query("""
        SELECT COUNT(DISTINCT b.book_id)
        FROM book b
        JOIN book_genre bg ON bg.book_id = b.book_id
        AND b.status != 'NOT_AVAILABLE'
        WHERE bg.genre_id = :id
    """)
    fun countByGenreId(@Param("id") genreId: Long): Long

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.status,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genre g ON g.genre_id = bg.genre_id
        WHERE (:bookName IS NULL OR LOWER(b.book_name) LIKE CONCAT('%', LOWER(:bookName), '%'))
        AND (
            :authorName IS NULL
            OR LOWER(a.author_name) LIKE CONCAT('%', LOWER(:authorName), '%')
            OR (a.author_name % :authorName AND SIMILARITY(a.author_name, LOWER(:authorName)) > 0.4)
        )
        AND (COALESCE(:genres, NULL) IS NULL OR bg.genre_id in (:genres))
        AND b.status != 'NOT_AVAILABLE'
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findPageWithAllParams(
        @Param("bookName") bookName: String?,
        @Param("authorName") authorName: String?,
        @Param("genres") genres: List<Int>?,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<Book>

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.status,
            rq.user_id AS rent_user_id, brq.user_id AS reservation_user_id,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genre g ON g.genre_id = bg.genre_id
        LEFT JOIN book_rent_queue rq ON rq.book_id = b.book_id
        LEFT JOIN book_reservation_queue brq ON brq.book_id = b.book_id
        WHERE (:bookName IS NULL OR LOWER(b.book_name) LIKE CONCAT('%', LOWER(:bookName), '%'))
        AND (
            :authorName IS NULL
            OR LOWER(a.author_name) LIKE CONCAT('%', LOWER(:authorName), '%')
            OR (a.author_name % :authorName AND SIMILARITY(a.author_name, LOWER(:authorName)) > 0.4)
        )
        AND (COALESCE(:genres, NULL) IS NULL OR bg.genre_id in (:genres))
        AND b.status = :status
        GROUP BY b.book_id, a.author_id, rq.user_id, brq.user_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findManagementPage(
        @Param("status") bookStatus: BookStatus,
        @Param("bookName") bookName: String?,
        @Param("authorName") authorName: String?,
        @Param("genres") genres: List<Int>?,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<BookInfo>

    @Query("""
        SELECT COUNT(DISTINCT b.book_id)
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        WHERE (:bookName IS NULL OR LOWER(b.book_name) LIKE CONCAT('%', LOWER(:bookName), '%'))
        AND (
            :authorName IS NULL
            OR LOWER(a.author_name) LIKE CONCAT('%', LOWER(:authorName), '%')
            OR (a.author_name % :authorName AND SIMILARITY(a.author_name, LOWER(:authorName)) > 0.4)
        )
        AND (COALESCE(:genres, NULL) IS NULL OR bg.genre_id in (:genres))
        AND b.status = :status
    """)
    fun countManagement(
        @Param("status") bookStatus: BookStatus,
        @Param("bookName") bookName: String?,
        @Param("authorName") authorName: String?,
        @Param("genres") genres: List<Int>?
    ): Long

    @Query("""
        SELECT COUNT(DISTINCT b.book_id)
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        WHERE (:bookName IS NULL OR LOWER(b.book_name) LIKE CONCAT('%', LOWER(:bookName), '%'))
        AND (
            :authorName IS NULL
            OR LOWER(a.author_name) LIKE CONCAT('%', LOWER(:authorName), '%')
            OR (a.author_name % :authorName AND SIMILARITY(a.author_name, LOWER(:authorName)) > 0.4)
        )
        AND (COALESCE(:genres, NULL) IS NULL OR bg.genre_id in (:genres))
        AND b.status != 'NOT_AVAILABLE'
    """)
    fun countWithAllParams(
        @Param("bookName") bookName: String?,
        @Param("authorName") authorName: String?,
        @Param("genres") genres: List<Int>?
    ): Long

    @Modifying
    @Query("""
        UPDATE book SET status = :status
        WHERE book_id IN (:ids)
    """)
    fun updateBooksStatus(@Param("ids") ids: List<Long>, @Param("status") status: BookStatus)

    @Modifying
    @Query("""
        UPDATE book SET status = :status
        WHERE book_id = :id
    """)
    fun updateBookStatus(@Param("id") id: Long, @Param("status") status: BookStatus)

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.status,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genre g ON g.genre_id = bg.genre_id
        JOIN book_reservation_queue brq ON b.book_id = brq.book_id
        WHERE brq.user_id = :id
        AND b.status != 'NOT_AVAILABLE'
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findRentedBooksByUserId(
        @Param("id") userId: UUID,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<Book>

    @Query("""
        SELECT COUNT(b.book_id)
        FROM book b
        JOIN book_reservation_queue brq ON b.book_id = brq.book_id
        WHERE brq.user_id = :id
        AND b.status != 'NOT_AVAILABLE'
    """)
    fun countRentedBooksByUserId(@Param("id") userId: UUID): Long

    @Modifying
    @Query("""
        INSERT INTO book_genre(book_id, genre_id)
        VALUES (:bookId, unnest(array[:genres]))
    """)
    fun linkWithGenres(bookId: Long, genres: List<Int>)
}
