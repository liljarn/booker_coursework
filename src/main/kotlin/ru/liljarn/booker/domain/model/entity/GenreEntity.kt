package ru.liljarn.booker.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("genre")
data class GenreEntity(
    @Id
    val genreId : Long,
    val genreName: String
)
