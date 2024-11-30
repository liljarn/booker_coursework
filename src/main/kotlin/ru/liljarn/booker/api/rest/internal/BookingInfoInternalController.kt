package ru.liljarn.booker.api.rest.internal

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljarn.booker.domain.service.BookingInfoService
import ru.liljarn.booker.support.consts.GANDALF
import ru.liljarn.booker.support.reflection.InternalApi
import java.util.*

@RestController
@RequestMapping("/api/v1/internal/booking")
class BookingInfoInternalController(
    private val bookingInfoService: BookingInfoService,
) {

    @InternalApi(GANDALF)
    @GetMapping("/{userId}")
    fun getClientBookingInfo(@PathVariable userId: UUID) = bookingInfoService.getClientCurrentBookingInfo(userId)
}
