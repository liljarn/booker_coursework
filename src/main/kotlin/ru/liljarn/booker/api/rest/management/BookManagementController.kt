package ru.liljarn.booker.api.rest.management

import org.springframework.web.bind.annotation.*
import ru.liljarn.booker.api.model.request.AddBookRequest
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.service.BookService
import ru.liljarn.booker.support.reflection.ManagementApi

@ManagementApi
@RestController
@RequestMapping("/api/v1/management/book")
class BookManagementController(
    private val bookService: BookService
) {
    @PostMapping("/{authorId}")
    fun addNewBook(@PathVariable authorId: Long, @ModelAttribute request: AddBookRequest) =
        bookService.addBook(authorId, request)

    @DeleteMapping("/{bookId}")
    fun deleteBook(@PathVariable bookId: Long) = bookService.markAsDeleted(bookId)

    @PutMapping("/{bookId}")
    fun updateBook(@PathVariable bookId: Long) {

    }

    @GetMapping("/{status}")
    fun getManagementBooksPage(
        @PathVariable status: BookStatus,
        @RequestParam page: Int,
        @RequestParam bookName: String?,
        @RequestParam author: String?,
        @RequestParam genres: List<Int>?
    ) = bookService.findManagementBooksPage(page, status, bookName, author, genres)
}
