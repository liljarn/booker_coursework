package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.domain.model.dto.notifications.StartRentNotificationEvent
import ru.liljarn.booker.domain.model.entity.RentEntity
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.model.type.NotificationType
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.domain.repository.RentRepository
import ru.liljarn.booker.domain.repository.ReservationRepository
import ru.liljarn.booker.infrastructure.grpc.GandalfService
import ru.liljarn.booker.support.mapper.toPage
import ru.liljarn.booker.support.mapper.toUserData
import ru.liljarn.booker.support.pageRequest
import java.time.LocalDate
import java.util.*

@Service
class RentService(
    private val rentRepository: RentRepository,
    private val bookRepository: BookRepository,
    private val reservationRepository: ReservationRepository,
    private val notificationService: NotificationService,
    private val gandalfService: GandalfService
) {

    @Transactional
    fun acceptRent(bookId: Long) {
        val clientReservation = reservationRepository.findByBookId(bookId) ?: return

        val dueDate = LocalDate.now().plusMonths(1)

        RentEntity(
            rentId = UUID.randomUUID(),
            bookId = bookId,
            userId = clientReservation.userId,
            dueDate = dueDate,
        ).also { rentRepository.makeRent(it) }

        bookRepository.updateBookStatus(bookId, BookStatus.READING)

        val userData = gandalfService.getUserById(clientReservation.userId).toUserData()
        val book = bookRepository.findByBookId(bookId)!!

        notificationService.sendNotification(
            StartRentNotificationEvent(
                email = userData.email,
                eventType = NotificationType.START_RENT,
                bookName = book.bookName,
                firstName = userData.firstName,
                authorName = book.authorName,
                dueDate = dueDate,
                currentDate = LocalDate.now(),
            )
        )

        reservationRepository.delete(clientReservation)
    }

    @Transactional
    fun endRent(bookId: Long) {
        val clientRent = rentRepository.findByBookIdAndDeletedAtIsNull(bookId) ?: return

        clientRent.apply {
            softDelete()
            rentRepository.save(this)
        }

        bookRepository.updateBookStatus(bookId, BookStatus.AVAILABLE)
    }

    @Transactional(readOnly = true)
    fun getClientRentHistory(page: Int, userId: UUID) = pageRequest(page) {
        bookRepository.findRentedBooksByUserId(userId, it.pageSize, it.offset)
            .toPage(bookRepository.countRentedBooksByUserId(userId))
    }
}
