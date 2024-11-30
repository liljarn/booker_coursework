package ru.liljarn.booker.jobs.handler

import org.springframework.stereotype.Component
import ru.liljarn.booker.domain.model.type.DailyJobType
import ru.liljarn.booker.domain.service.ReservationService

@Component
class CancelOverdueReservationProcessor(
    private val reservationService: ReservationService,
) : DailyStatusProcessor {

    override val event = DailyJobType.CANCEL_OVERDUE_RESERVATION

    override fun process() = reservationService.removeOverdueReservations()
}
