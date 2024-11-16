package ru.liljarn.booker.api.rest

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljarn.booker.api.security.userContext
import ru.liljarn.booker.domain.service.RentService

@RestController
@RequestMapping("/api/v1/rent")
class RentController(
    private val rentService: RentService
) {

    @PostMapping("/{bookId}")
    fun rentBook(@PathVariable bookId: Long) = userContext {
        rentService.rentBook(bookId)
    }
}