package ru.liljarn.booker.domain.model.dto

data class CommentPage(
    val total: Long,
    val comments: List<Comment>
)
