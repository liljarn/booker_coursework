package ru.liljarn.booker.jobs

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.liljarn.booker.jobs.handler.DailyStatusProcessor

private val logger = KotlinLogging.logger {}

@Component
class DailyStatusCheckJob(
    @Value("\${jobs.reservationCheck.enabled}")
    private val jobEnabled: Boolean,
    private val handlers: List<DailyStatusProcessor>
) {

    @Scheduled(cron = "\${jobs.reservationCheck.cron}")
    fun checkBookingStatus() {
        if (jobEnabled) {
            handlers.forEach {
                logger.info { "Start processing daily job, type=${it.event}" }

                it.process()

                logger.info { "Finish processing daily job, type=${it.event}" }
            }
        }
    }
}
