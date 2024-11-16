package ru.liljarn.booker.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("author")
data class AuthorEntity(
    @Id
    val authorId: Long,
    val authorName: String,
    val photoUrl: String
)
