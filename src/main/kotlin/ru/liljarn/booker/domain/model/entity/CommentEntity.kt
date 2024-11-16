package ru.liljarn.booker.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("comment")
data class CommentEntity(
    @Id
    val commentId : UUID,
    val userId : UUID,
    val comment: String,
    val rating: Int,
    val bookId: Long
)
