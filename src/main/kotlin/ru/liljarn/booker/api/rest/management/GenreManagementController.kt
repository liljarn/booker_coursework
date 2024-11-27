package ru.liljarn.booker.api.rest.management

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljarn.booker.api.model.request.AddGenreRequest
import ru.liljarn.booker.domain.service.GenreService
import ru.liljarn.booker.support.reflection.ManagementApi

@ManagementApi
@RestController
@RequestMapping("/api/v1/management/genre")
class GenreManagementController(
    private val genreService: GenreService
) {

    @PostMapping
    fun addGenre(@RequestBody request: AddGenreRequest) = genreService.addGenre(request)
}
