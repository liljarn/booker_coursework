package ru.liljarn.booker.domain.model.dto

data class BookDetailed(
    val book: Book,
    val userData: UserData?,
    val self: Boolean
)
