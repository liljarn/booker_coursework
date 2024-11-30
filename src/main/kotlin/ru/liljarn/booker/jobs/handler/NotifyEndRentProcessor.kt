package ru.liljarn.booker.jobs.handler

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.domain.model.dto.notifications.EndRentNotificationEvent
import ru.liljarn.booker.domain.model.type.DailyJobType
import ru.liljarn.booker.domain.model.type.NotificationType
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.domain.repository.RentRepository
import ru.liljarn.booker.domain.service.NotificationService
import ru.liljarn.booker.infrastructure.grpc.GandalfService
import ru.liljarn.booker.support.mapper.toUserData
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

@Component
class NotifyEndRentProcessor(
    private val rentRepository: RentRepository,
    private val bookRepository: BookRepository,
    private val gandalfService: GandalfService,
    private val notificationService: NotificationService
) : DailyStatusProcessor {

    override val event = DailyJobType.NOTIFY_END_RENT

    @Transactional(readOnly = true)
    override fun process() {
        val currentDate = LocalDate.now()
        val borderDate = currentDate.plusDays(3)
        val userIds = rentRepository.findByDueDateBetweenAndDeletedAtIsNull(currentDate, borderDate)
        logger.info { userIds.first() }
        userIds.forEach {
            val userData = gandalfService.getUserById(it.userId).toUserData()
            val book = bookRepository.findByBookId(it.bookId)!!

            notificationService.sendNotification(
                EndRentNotificationEvent(
                    userData.email,
                    NotificationType.END_RENT,
                    userData.firstName,
                    book.bookName,
                    book.authorName,
                    it.dueDate
                )
            )
        }
    }
}
