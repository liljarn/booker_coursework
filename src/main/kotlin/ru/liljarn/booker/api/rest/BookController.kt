package ru.liljarn.booker.api.rest

import org.springframework.web.bind.annotation.*
import ru.liljarn.booker.domain.service.BookService

@RestController
@RequestMapping("api/v1/book")
class BookController(
    private val bookService: BookService
) {

    @GetMapping("/info/{bookId}")
    fun getBookInfo(@PathVariable bookId: Long) = bookService.findBook(bookId)

    @GetMapping("/list")
    fun getBooksPage(
        @RequestParam page: Int,
        @RequestParam bookName: String?,
        @RequestParam author: String?
    ) =
        bookService.findBooksPage(page, bookName, author)

    @GetMapping("/list/author/{authorId}")
    fun getAuthorBooks(@PathVariable authorId: Long, @RequestParam page: Int) =
        bookService.findAuthorBooksPage(authorId, page)
}
