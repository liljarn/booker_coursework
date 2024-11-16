package ru.liljarn.booker.api.model.request

data class CommentRequest(
    val comment: String,
    val rating: Int
)
