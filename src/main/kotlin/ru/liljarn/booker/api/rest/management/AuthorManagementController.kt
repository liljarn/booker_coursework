package ru.liljarn.booker.api.rest.management

import org.springframework.web.bind.annotation.*
import ru.liljarn.booker.api.model.request.AddAuthorRequest
import ru.liljarn.booker.api.rest.management.support.ManagementApi
import ru.liljarn.booker.domain.service.AuthorService

@ManagementApi
@RestController
@RequestMapping("/api/v1/management/author")
class AuthorManagementController(
    private val authorService: AuthorService,
) {

    @PostMapping
    fun addAuthor(@ModelAttribute request: AddAuthorRequest) = authorService.addAuthor(request)
}
