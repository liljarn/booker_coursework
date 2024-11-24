package ru.liljarn.booker.api.rest.management

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljarn.booker.api.rest.management.support.ManagementApi
import ru.liljarn.booker.domain.service.RentService

@ManagementApi
@RestController
@RequestMapping("/api/v1/management/rent")
class RentManagementController(
    private val rentService: RentService,
) {

    @PostMapping("/{bookId}")
    fun acceptClientRent(@PathVariable bookId: Long) = rentService.acceptRent(bookId)

    @DeleteMapping("/{bookId}")
    fun endClientRent(@PathVariable bookId: Long) = rentService.endRent(bookId)
}
