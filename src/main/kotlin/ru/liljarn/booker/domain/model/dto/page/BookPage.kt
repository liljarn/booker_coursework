package ru.liljarn.booker.domain.model.dto.page

import ru.liljarn.booker.domain.model.dto.Book

data class BookPage(
    val total: Long,
    val books: List<Book>
)
