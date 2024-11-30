package ru.liljarn.booker.domain.model.dto.notifications

import ru.liljarn.booker.domain.model.type.NotificationType

interface Event {
    val email: String
    val eventType: NotificationType
}
