package ru.liljarn.booker.domain.model.dto

data class AuthorPage(
    val total: Long,
    val authors: List<Author>
)
