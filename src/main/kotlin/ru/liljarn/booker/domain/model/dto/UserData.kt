package ru.liljarn.booker.domain.model.dto

import java.time.LocalDate
import java.util.UUID

data class UserData(
    val userId: UUID,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val photoUrl: String
)
