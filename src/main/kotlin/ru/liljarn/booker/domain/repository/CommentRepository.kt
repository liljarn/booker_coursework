package ru.liljarn.booker.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.entity.CommentEntity
import java.util.*

@Repository
interface CommentRepository : CrudRepository<CommentEntity, UUID> {

    fun findAllByBookId(page: Pageable, bookId: Long): Page<CommentEntity>

    fun findAllByUserId(page: Pageable, userId: UUID): Page<CommentEntity>

    @Modifying
    @Query("""
        INSERT INTO comment (comment_id, user_id, comment, rating, book_id)
        VALUES (:#{#comment.commentId}, :#{#comment.userId}, :#{#comment.comment}, :#{#comment.rating}, :#{#comment.bookId})
    """)
    fun save(@Param("comment") commentEntity: CommentEntity)
}
