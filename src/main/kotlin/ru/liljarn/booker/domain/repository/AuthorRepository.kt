package ru.liljarn.booker.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.entity.AuthorEntity

@Repository
interface AuthorRepository : CrudRepository<AuthorEntity, Long> {

    fun findAll(page: Pageable): Page<AuthorEntity>

    fun findAllByAuthorNameContainingIgnoreCase(authorName: String, page: Pageable): Page<AuthorEntity>

    @Query("""
        SELECT author_name FROM author
        WHERE author_id = :authorId
    """)
    fun findAuthorNameByAuthorId(authorId: Long): String
}
