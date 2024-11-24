package ru.liljarn.booker.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.*

@Table("book_reservation_queue")
data class ReservationEntity(
    @Id
    val reservationId: UUID,
    val bookId: Long,
    val userId: UUID,
    val dueDate: LocalDate,
)
