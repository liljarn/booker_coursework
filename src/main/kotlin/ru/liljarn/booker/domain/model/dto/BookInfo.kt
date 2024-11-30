package ru.liljarn.booker.domain.model.dto

import org.springframework.data.annotation.Reference
import ru.liljarn.booker.domain.model.type.BookStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class BookInfo(
    val bookId: Long,
    val bookName: String,
    val authorId: Long,
    val authorName: String,
    val authorPhotoUrl: String,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val photoUrl: String,
    val rating: BigDecimal,
    val status: BookStatus = BookStatus.AVAILABLE,
    val rentUserId: UUID?,
    val reservationUserId: UUID?,
    val rentDueDate: LocalDate?,
    val reservationDueDate: LocalDate?,
    @Reference
    val genres: ArrayList<Genre>
)
