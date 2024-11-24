package ru.liljarn.booker.jobs

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.liljarn.booker.domain.service.ReservationService

@Component
class ReservationCheckJob(
    @Value("\${jobs.reservationCheck.enabled}")
    private val jobEnabled: Boolean,
    private val reservationService: ReservationService
) {

    @Scheduled(cron = "\${jobs.reservationCheck.cron}")
    fun checkReservation() {
        if (jobEnabled) {
            reservationService.removeOverdueReservations()
        }
    }
}
