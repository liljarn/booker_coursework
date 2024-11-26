package ru.liljarn.booker.domain.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.api.security.nullableUser
import ru.liljarn.booker.api.security.userId
import ru.liljarn.booker.domain.model.dto.BookDetailed
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.infrastructure.grpc.GandalfService
import ru.liljarn.booker.support.mapper.toBook
import ru.liljarn.booker.support.mapper.toBookDetailed
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.mapper.toUserData
import ru.liljarn.booker.support.pageRequest

private val logger = KotlinLogging.logger {}

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val gandalfService: GandalfService
) {

    fun findBook(bookId: Long): BookDetailed {
        val bookInfo = bookRepository.findByBookId(bookId) ?: throw RuntimeException()

        val userDataResponse = bookInfo.rentUserId?.let {
            gandalfService.getUserById(it)
        } ?: bookInfo.reservationUserId?.let {
            gandalfService.getUserById(it)
        }

        val userData = userDataResponse?.toUserData()

        return bookInfo.toBook().toBookDetailed(userData, nullableUser?.userId)
    }

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
