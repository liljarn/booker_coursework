package ru.liljarn.booker.domain.model.dto.page

import ru.liljarn.booker.domain.model.dto.Comment

data class CommentPage(
    val total: Long,
    val comments: List<Comment>
)
