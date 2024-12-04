package ru.liljarn.booker.domain.model.dto.page

import ru.liljarn.booker.domain.model.dto.BookManagement

data class BookManagementPage(
    val total: Long,
    val managementBooks: List<BookManagement>,
)
