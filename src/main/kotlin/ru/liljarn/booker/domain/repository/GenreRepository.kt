package ru.liljarn.booker.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.entity.GenreEntity

@Repository
interface GenreRepository : CrudRepository<GenreEntity, Long> {
    fun findAll(page: Pageable): Page<GenreEntity>

    fun findAllByGenreNameContainingIgnoreCase(genreName: String, page: Pageable): Page<GenreEntity>
}
