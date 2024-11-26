package ru.liljarn.booker.domain.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.*

data class UserData(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate,
    val photoUrl: String
)
