package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import ru.liljarn.booker.domain.repository.GenreRepository
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.pageRequest

@Service
class GenreService(
    private val genreRepository: GenreRepository
) {

    fun getGenresPage(page: Int) = pageRequest(page) {
        genreRepository.findAll(it)
    }.toDto()

    fun getGenresPageByName(page: Int, text: String) = pageRequest(page) {
        genreRepository.findAllByGenreNameContainingIgnoreCase(text, it)
    }.toDto()
}
