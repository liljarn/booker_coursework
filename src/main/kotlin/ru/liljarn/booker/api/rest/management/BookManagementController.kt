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

    @GetMapping("/{status}")
    fun getManagementBooksPageWithStatus(
        @PathVariable status: BookStatus,
        @RequestParam page: Int,
        @RequestParam bookName: String?,
        @RequestParam author: String?,
        @RequestParam genres: List<Int>?
    ) = bookService.findManagementBooksPageWithStatus(page, status, bookName, author, genres)

    @GetMapping
    fun getManagementBooksPage(
        @RequestParam page: Int,
        @RequestParam bookName: String?,
        @RequestParam author: String?,
        @RequestParam genres: List<Int>?
    ) = bookService.findManagementBooksPage(page, bookName, author, genres)
}
