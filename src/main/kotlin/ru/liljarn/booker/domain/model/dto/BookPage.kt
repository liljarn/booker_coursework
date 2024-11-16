package ru.liljarn.booker.domain.model.dto

data class BookPage(
    val total: Long,
    val books: List<Book>
)
