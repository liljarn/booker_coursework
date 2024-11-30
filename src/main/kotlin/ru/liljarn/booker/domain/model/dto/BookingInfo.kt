package ru.liljarn.booker.domain.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class BookingInfo(
    val book: Book,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val dueDate: LocalDate,
)
