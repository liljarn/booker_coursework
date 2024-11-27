package ru.liljarn.booker.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.liljarn.booker.domain.model.type.BookStatus
import java.math.BigDecimal

@Table("book")
data class BookEntity(
    val bookName: String,
    val authorId: Long,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val rating: BigDecimal = BigDecimal.ZERO,
    val photoUrl: String
) {
    @Id
    var bookId: Long? = null
    var status: BookStatus = BookStatus.AVAILABLE
}
