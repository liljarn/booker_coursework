package ru.liljarn.booker.api.rest.management

import org.springframework.web.bind.annotation.*
import ru.liljarn.booker.api.rest.management.support.ManagementApi
import ru.liljarn.booker.domain.service.BookService

@ManagementApi
@RestController
@RequestMapping("/api/v1/management/book")
class BookManagementController(
    private val bookService: BookService
) {
    @PostMapping
    fun addNewBook() {
        println("ABOBUS")
    }

    @DeleteMapping("/{bookId}")
    fun deleteBook(@PathVariable bookId: Long) = bookService.markAsDeleted(bookId)

    @PutMapping("/{bookId}")
    fun updateBook(@PathVariable bookId: Long) {

    }
}
