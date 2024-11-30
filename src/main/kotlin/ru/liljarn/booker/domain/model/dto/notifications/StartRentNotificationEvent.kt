package ru.liljarn.booker.domain.model.dto.notifications

import ru.liljarn.booker.domain.model.type.NotificationType
import java.time.LocalDate

data class StartRentNotificationEvent(
    override val email: String,
    override val eventType: NotificationType,
    val firstName: String,
    val bookName: String,
    val authorName: String,
    val dueDate: LocalDate,
    val currentDate: LocalDate
) : Event
