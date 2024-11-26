package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import ru.liljarn.booker.domain.repository.GenreRepository
import ru.liljarn.booker.support.mapper.toDto

@Service
class GenreService(
    private val genreRepository: GenreRepository
) {

    fun getGenres() = genreRepository.findAll().map { it.toDto() }

    fun getGenresPageByName(text: String) =
        genreRepository.findAllByGenreNameContainingIgnoreCase(text).map { it.toDto() }
}
