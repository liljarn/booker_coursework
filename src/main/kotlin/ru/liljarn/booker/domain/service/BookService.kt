package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.pageRequest

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    fun findBooksPage(page: Int, bookName: String?, author: String?) = pageRequest(page) {
        bookRepository.findPage(bookName, author, it.pageSize, it.offset)
            .toDto(bookRepository.count(bookName, author))
    }

    fun findBook(bookId: Long) = bookRepository.findByBookId(bookId) ?: throw RuntimeException()

    fun findAuthorBooksPage(authorId: Long, page: Int) = pageRequest(page) {
        bookRepository.findAllByAuthorId(authorId, it)
    }
}
