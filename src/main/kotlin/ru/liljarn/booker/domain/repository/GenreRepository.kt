package ru.liljarn.booker.domain.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.entity.GenreEntity

@Repository
interface GenreRepository : CrudRepository<GenreEntity, Int> {
    fun findAllByGenreNameContainingIgnoreCase(genreName: String): List<GenreEntity>
}
