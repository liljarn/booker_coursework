package ru.liljarn.booker.api.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.liljarn.booker.domain.service.AuthorService

@RestController
@RequestMapping("api/v1/author")
class AuthorController(
    private val authorService: AuthorService
) {
    @GetMapping("/list")
    fun getAuthors(@RequestParam page: Int) = authorService.getAuthorsPage(page)

    @GetMapping("/{authorId}")
    fun getAuthorById(@PathVariable authorId: Long) = authorService.getAuthorById(authorId)

    @GetMapping("/name")
    fun getAuthorsByName(@RequestParam page: Int, @RequestParam text: String) =
        authorService.getAuthorsPageByName(page, text)
}
