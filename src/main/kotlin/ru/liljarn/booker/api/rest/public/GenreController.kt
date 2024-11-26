package ru.liljarn.booker.api.rest.public

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.liljarn.booker.domain.service.GenreService

@RestController
@RequestMapping("/api/v1/genre")
class GenreController(
    private val genreService: GenreService
) {

    @GetMapping
    fun getGenres() = genreService.getGenres()

    @GetMapping("/name")
    fun getGenresByName(@RequestParam text: String) =
        genreService.getGenresPageByName(text)
}
