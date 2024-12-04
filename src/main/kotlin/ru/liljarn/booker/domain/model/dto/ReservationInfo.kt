package ru.liljarn.booker.domain.model.dto

import java.time.LocalDate
import java.util.UUID

data class ReservationInfo(
    val bookId: Long,
    val userId: UUID?,
    val dueDate: LocalDate?
)
