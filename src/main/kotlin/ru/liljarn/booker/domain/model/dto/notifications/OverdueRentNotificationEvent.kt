package ru.liljarn.booker.domain.model.dto.notifications

import ru.liljarn.booker.domain.model.type.NotificationType

data class OverdueRentNotificationEvent(
    override val email: String,
    override val eventType: NotificationType,
    val firstName: String,
    val bookName: String,
    val authorName: String,
) : Event
