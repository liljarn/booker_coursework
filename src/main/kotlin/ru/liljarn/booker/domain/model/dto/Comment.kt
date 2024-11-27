package ru.liljarn.booker.domain.model.dto

import java.util.*

data class Comment(
    val commentId: UUID,
    val userData: UserData,
    val comment: String,
    val rating: Int,
    val bookId: Long,
    val self: Boolean
)
