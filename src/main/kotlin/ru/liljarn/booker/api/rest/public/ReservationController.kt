package ru.liljarn.booker.api.rest.public

import org.springframework.web.bind.annotation.*
import ru.liljarn.booker.api.model.request.ReservationRequest
import ru.liljarn.booker.api.security.userContext
import ru.liljarn.booker.domain.service.ReservationService

@RestController
@RequestMapping("/api/v1/reservation")
class ReservationController(
    private val reservationService: ReservationService
) {
    @PostMapping("/{bookId}")
    fun reserveBook(@PathVariable bookId: Long, @RequestBody dateRequest: ReservationRequest) = userContext {
        reservationService.reserveBook(bookId, dateRequest)
    }

    @DeleteMapping
    fun cancelBookReservation() = userContext {
        reservationService.removeClientReservation()
    }

    @GetMapping
    fun getReservation() = userContext {
        reservationService.getClientReservation()
    }
}
