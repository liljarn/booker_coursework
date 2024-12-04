package ru.liljarn.booker.domain.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.api.model.request.AddBookRequest
import ru.liljarn.booker.domain.model.dto.BookDetailed
import ru.liljarn.booker.domain.model.dto.BookManagement
import ru.liljarn.booker.domain.model.entity.BookEntity
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.domain.repository.RentRepository
import ru.liljarn.booker.domain.repository.ReservationRepository
import ru.liljarn.booker.infrastructure.grpc.GandalfService
import ru.liljarn.booker.support.mapper.*
import ru.liljarn.booker.support.pageRequest
import ru.liljarn.booker.support.security.nullableUser
import ru.liljarn.booker.support.security.userId
import java.util.*

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val rentRepository: RentRepository,
    private val reservationRepository: ReservationRepository,
    private val gandalfService: GandalfService,
    @Qualifier("bookMinioImageService")
    private val imageService: ImageService
) {

    fun findBook(bookId: Long): BookDetailed {
        val book = bookRepository.findByBookId(bookId) ?: throw RuntimeException()
        val userDataResponse = getUserDataResponse(
            rentRepository.findByBookIdAndDeletedAtIsNull(book.bookId)?.userId,
            reservationRepository.findByBookId(book.bookId)?.userId,
        )

        val userData = userDataResponse?.toUserData()

        return book.toBookDetailed(userData, nullableUser?.userId)
    }

    @Transactional(readOnly = true)
    fun findBooksPage(page: Int, bookName: String?, author: String?, genres: List<Int>?) = pageRequest(page) {
        bookRepository.findPageWithAllParams(bookName, author, genres, it.pageSize, it.offset)
            .toPage(bookRepository.countWithAllParams(bookName, author, genres))
    }

    @Transactional(readOnly = true)
    fun findAuthorBooksPage(authorId: Long, page: Int) = pageRequest(page) {
        bookRepository.findPageByAuthorId(authorId, it.pageSize, it.offset)
            .toPage(bookRepository.countByAuthorId(authorId))
    }

    @Transactional(readOnly = true)
    fun findBooksByGenre(genreId: Long, page: Int) = pageRequest(page) {
        bookRepository.findPageByGenreId(genreId, it.pageSize, it.offset)
            .toPage(bookRepository.countByGenreId(genreId))
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
        bookName: String?,
        author: String?,
        genres: List<Int>?
    ) = pageRequest(page) { pageRequest ->
        val booksPage =
            bookRepository.findManagementPage(bookName, author, genres, pageRequest.pageSize, pageRequest.offset)
        val bookIds = booksPage.map { it.bookId }
        val reservationInfoMap = reservationRepository.findReservationInfo(bookIds).associateBy { it.bookId }
        val rentInfoMap = rentRepository.findRentInfo(bookIds).associateBy { it.bookId }

        booksPage.map { book ->
            val reservationInfo = reservationInfoMap[book.bookId]
            val rentInfo = rentInfoMap[book.bookId]
            BookManagement(
                book = book,
                userData = getUserDataResponse(
                    rentInfo?.userId,
                    reservationInfo?.userId
                )?.toUserData(),
                dueDate = rentInfo?.dueDate ?: reservationInfo?.dueDate
            )
        }
    }.toPage(bookRepository.countManagement(bookName, author, genres))

    @Transactional(readOnly = true)
    fun findManagementBooksPageWithStatus(
        page: Int,
        status: BookStatus,
        bookName: String?,
        author: String?,
        genres: List<Int>?
    ) = pageRequest(page) {
        val booksPage =
            bookRepository.findManagementPageWithStatus(status, bookName, author, genres, it.pageSize, it.offset)
        val bookIds = booksPage.map { it.bookId }
        val reservationInfoMap = reservationRepository.findReservationInfo(bookIds).associateBy { it.bookId }
        val rentInfoMap = rentRepository.findRentInfo(bookIds).associateBy { it.bookId }

        booksPage.map { book ->
            val reservationInfo = reservationInfoMap[book.bookId]
            val rentInfo = rentInfoMap[book.bookId]
            BookManagement(
                book = book,
                userData = getUserDataResponse(
                    rentInfo?.userId,
                    reservationInfo?.userId
                )?.toUserData(),
                dueDate = rentInfo?.dueDate ?: reservationInfo?.dueDate
            )
        }
    }.toPage(bookRepository.countManagementWithStatus(status, bookName, author, genres))

    @Transactional
    fun addBook(authorId: Long, request: AddBookRequest) {
        val photoUrl = imageService.uploadImage(request.photo.inputStream, request.bookName)

        val bookEntity = BookEntity(
            bookName = request.bookName,
            authorId = authorId,
            releaseYear = request.releaseYear.toShort(),
            ageLimit = request.ageLimit.toShort(),
            description = request.description,
            photoUrl = photoUrl
        )

        val book = bookRepository.save(bookEntity)
        bookRepository.linkWithGenres(book.bookId!!, request.genres)
    }

    private fun getUserDataResponse(rentUserId: UUID?, reservationUserId: UUID?) =
        rentUserId?.let { gandalfService.getUserById(it) } ?: reservationUserId?.let { gandalfService.getUserById(it) }
}
