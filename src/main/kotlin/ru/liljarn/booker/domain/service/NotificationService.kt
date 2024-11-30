package ru.liljarn.booker.domain.service

import ru.liljarn.booker.domain.model.dto.notifications.Event

interface NotificationService {

    fun sendNotification(event: Event)
}
