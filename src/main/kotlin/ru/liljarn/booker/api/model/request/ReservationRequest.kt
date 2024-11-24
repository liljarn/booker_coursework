package ru.liljarn.booker.api.model.request

import java.time.LocalDate

data class ReservationRequest(
    val dueDate: LocalDate
)
