package ru.liljarn.booker.domain.model.dto

data class BookManagementPage(
    val total: Long,
    val managementBooks: List<BookManagement>,
)
