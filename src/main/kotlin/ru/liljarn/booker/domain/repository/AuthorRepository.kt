package ru.liljarn.booker.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.entity.AuthorEntity

@Repository
interface AuthorRepository : CrudRepository<AuthorEntity, Long> {

    fun findAll(page: Pageable): Page<AuthorEntity>

    fun findAllByAuthorNameContainingIgnoreCase(authorName: String, page: Pageable): Page<AuthorEntity>

    @Modifying
    @Query("""
        INSERT INTO author (author_name, author_photo_url)
        VALUES (:#{#author.authorName}, :#{#author.authorPhotoUrl})
    """)
    fun addNewAuthor(@Param("author") author: AuthorEntity)
}
