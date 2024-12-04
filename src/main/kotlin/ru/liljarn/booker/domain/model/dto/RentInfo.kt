package ru.liljarn.booker.domain.model.dto

import java.time.LocalDate
import java.util.*

data class RentInfo(
    val bookId: Long,
    val userId: UUID?,
    val dueDate: LocalDate?
)
