package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.pageRequest

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    fun findBook(bookId: Long) = bookRepository.findByBookId(bookId) ?: throw RuntimeException()

    @Transactional(readOnly = true)
    fun findBooksPage(page: Int, bookName: String?, author: String?, genres: List<Int>?) = pageRequest(page) {
        bookRepository.findPageWithAllParams(bookName, author, genres, it.pageSize, it.offset)
            .toDto(bookRepository.countWithAllParams(bookName, author, genres))
    }

    @Transactional(readOnly = true)
    fun findAuthorBooksPage(authorId: Long, page: Int) = pageRequest(page) {
        bookRepository.findPageByAuthorId(authorId, it.pageSize, it.offset)
            .toDto(bookRepository.countByAuthorId(authorId))
    }

    @Transactional(readOnly = true)
    fun findBooksByGenre(genreId: Long, page: Int) = pageRequest(page) {
        bookRepository.findPageByGenreId(genreId, it.pageSize, it.offset)
            .toDto(bookRepository.countByGenreId(genreId))
    }

    fun markAsDeleted(bookId: Long) = bookRepository.updateBookStatus(bookId, BookStatus.NOT_AVAILABLE)
}
