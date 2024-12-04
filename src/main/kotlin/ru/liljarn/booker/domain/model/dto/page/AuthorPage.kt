package ru.liljarn.booker.domain.model.dto.page

import ru.liljarn.booker.domain.model.dto.Author

data class AuthorPage(
    val total: Long,
    val authors: List<Author>
)
