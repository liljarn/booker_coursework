package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.api.model.request.ReservationRequest
import ru.liljarn.booker.support.security.user
import ru.liljarn.booker.support.security.userId
import ru.liljarn.booker.domain.model.entity.ReservationEntity
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.domain.repository.ReservationRepository
import ru.liljarn.booker.support.mapper.toDto
import java.time.LocalDate
import java.util.*

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val bookRepository: BookRepository
) {

    @Transactional
    fun reserveBook(bookId: Long, request: ReservationRequest) {
        if (reservationRepository.existsByUserId(user.userId)) throw RuntimeException()

        val reservationEntity = ReservationEntity(
            reservationId = UUID.randomUUID(),
            bookId = bookId,
            userId = user.userId,
            dueDate = request.dueDate,
        )

        reservationRepository.makeReservation(reservationEntity)

        bookRepository.save(
            bookRepository.findById(bookId).get().apply {
                status = BookStatus.BOOKED
            }
        )
    }

    @Transactional
    fun removeOverdueReservations() {
        val currentDate = LocalDate.now()
        val overdueBooks = reservationRepository.findBookIdByDueDateBefore(currentDate)

        reservationRepository.deleteOverdueReservations(currentDate)

        if (overdueBooks.isNotEmpty()) {
            bookRepository.updateBooksStatus(overdueBooks, BookStatus.AVAILABLE)
        }
    }

    @Transactional
    fun removeClientReservation() = reservationRepository.findByUserId(user.userId)?.let {
        bookRepository.updateBookStatus(it.bookId, BookStatus.AVAILABLE)
        reservationRepository.delete(it)
    }

    fun getClientReservation() = reservationRepository.findByUserId(user.userId)?.toDto()
}
