package ru.liljarn.booker.domain.model.dto

data class GenrePage(
    val total: Long,
    val genres: List<Genre>
)
