package ru.liljarn.booker.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.liljarn.booker.domain.model.type.BookStatus
import java.math.BigDecimal

@Table("book")
data class BookEntity(
    @Id
    val bookId: Long,
    val bookName: String,
    val authorId: Long,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val status: BookStatus,
    val rating: BigDecimal = BigDecimal.ZERO,
    val photoUrl: String
)
