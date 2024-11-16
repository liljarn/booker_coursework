package ru.liljarn.booker.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.dto.Book
import ru.liljarn.booker.domain.model.entity.BookEntity

@Repository
interface BookRepository : CrudRepository<BookEntity, Long> {

    fun findByBookId(id: Long): BookEntity?

    fun findAllByAuthorId(authorId: Long, pageable: Pageable): Page<BookEntity>

    @Query("""
    SELECT b.book_id, b.book_name, a.author_id, a.author_name,
        a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating
    FROM book b
    JOIN author a ON b.author_id = a.author_id
    WHERE (:bookName IS NULL OR LOWER(b.book_name) LIKE CONCAT('%', LOWER(:bookName), '%'))
    AND (
        :authorName IS NULL
        OR LOWER(a.author_name) LIKE CONCAT('%', LOWER(:authorName), '%')
        OR a.author_name % :authorName
        AND SIMILARITY(a.author_name, LOWER(:authorName)) > 0.4
    )
    LIMIT :limit OFFSET :offset
""")
    fun findPage(
        @Param("bookName") bookName: String?,
        @Param("authorName") authorName: String?,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<Book>

    @Query("""
        SELECT COUNT(*)
        FROM book b
        JOIN author a ON b.author_id = a.author_id
        WHERE (:bookName IS NULL OR LOWER(b.book_name) LIKE CONCAT('%', LOWER(:bookName), '%'))
        AND (
            :authorName IS NULL
            OR LOWER(a.author_name) LIKE CONCAT('%', LOWER(:authorName), '%')
            OR a.author_name % :authorName
            AND SIMILARITY(a.author_name, LOWER(:authorName)) > 0.4
        )
    """)
    fun count(@Param("bookName") bookName: String?, @Param("authorName") authorName: String?): Long
}
