package ru.liljarn.booker.domain.model.dto.page

import ru.liljarn.booker.domain.model.dto.BookHistory

data class BookHistoryPage(
    val total: Long,
    val books: List<BookHistory>
)
