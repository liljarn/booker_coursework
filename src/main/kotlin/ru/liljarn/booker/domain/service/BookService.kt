package ru.liljarn.booker.domain.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.api.model.request.AddBookRequest
import ru.liljarn.booker.domain.model.dto.BookDetailed
import ru.liljarn.booker.domain.model.dto.BookManagement
import ru.liljarn.booker.domain.model.entity.BookEntity
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.infrastructure.grpc.GandalfService
import ru.liljarn.booker.support.mapper.*
import ru.liljarn.booker.support.pageRequest
import ru.liljarn.booker.support.security.nullableUser
import ru.liljarn.booker.support.security.userId
import java.util.*

private val logger = KotlinLogging.logger {}

private val BOOK_CONTAINS_CLIENT_STATUS = hashSetOf(BookStatus.BOOKED, BookStatus.READING)

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val gandalfService: GandalfService,
    @Qualifier("bookMinioImageService")
    private val imageService: ImageService
) {

    fun findBook(bookId: Long): BookDetailed {
        val bookInfo = bookRepository.findByBookId(bookId) ?: throw RuntimeException()

        val userDataResponse = getUserDataResponse(bookInfo.rentUserId, bookInfo.reservationUserId)

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

    @Transactional
    fun markAsDeleted(bookId: Long) {
        val book = bookRepository.findById(bookId).orElseThrow { RuntimeException() }
        if (book.status == BookStatus.AVAILABLE) {
            book.apply { status = BookStatus.NOT_AVAILABLE }
            bookRepository.save(book)
        }
    }

    @Transactional(readOnly = true)
    fun findManagementBooksPage(
        page: Int,
        status: BookStatus,
        bookName: String?,
        author: String?,
        genres: List<Int>?
    ) = pageRequest(page) {
        val booksPage = bookRepository.findManagementPage(status, bookName, author, genres, it.pageSize, it.offset)

        if (BOOK_CONTAINS_CLIENT_STATUS.contains(status)) {
            booksPage.map { bookInfo ->
                BookManagement(
                    bookInfo.toBook(),
                    getUserDataResponse(bookInfo.rentUserId, bookInfo.reservationUserId)?.toUserData()
                )
            }
        } else {
            booksPage.map { bookInfo -> BookManagement(bookInfo.toBook(), null) }
        }
    }.toPage(bookRepository.countManagement(status, bookName, author, genres))

    @Transactional
    fun addBook(authorId: Long, request: AddBookRequest) {
        val photoUrl = imageService.uploadImage(request.photo.inputStream, request.bookName)

        val bookEntity = BookEntity(
            bookName = request.bookName,
            authorId = authorId,
            releaseYear = request.releaseYear,
            ageLimit = request.ageLimit,
            description = request.description,
            photoUrl = photoUrl
        )

        val book = bookRepository.save(bookEntity)
        bookRepository.linkWithGenres(book.bookId!!, request.genres)
    }

    private fun getUserDataResponse(rentUserId: UUID?, reservationUserId: UUID?) =
        rentUserId?.let { gandalfService.getUserById(it) } ?: reservationUserId?.let { gandalfService.getUserById(it) }
}
