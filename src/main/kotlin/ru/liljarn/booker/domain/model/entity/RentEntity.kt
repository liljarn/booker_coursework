package ru.liljarn.booker.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.UUID

@Table("book_rent_queue")
data class RentEntity(
    @Id
    val orderId: UUID,
    val bookId: Long,
    val userId: UUID,
    val dueDate: LocalDate
)
