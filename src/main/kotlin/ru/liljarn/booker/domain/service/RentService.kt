package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.domain.model.entity.RentEntity
import ru.liljarn.booker.domain.model.type.BookStatus
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.domain.repository.RentRepository
import ru.liljarn.booker.domain.repository.ReservationRepository
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.pageRequest
import java.time.LocalDate
import java.util.*

@Service
class RentService(
    private val rentRepository: RentRepository,
    private val bookRepository: BookRepository,
    private val reservationRepository: ReservationRepository
) {

    @Transactional
    fun acceptRent(bookId: Long) {
        val clientReservation = reservationRepository.findByBookId(bookId) ?: return

        RentEntity(
            rentId = UUID.randomUUID(),
            bookId = bookId,
            userId = clientReservation.userId,
            dueDate = LocalDate.now().plusMonths(1),
        ).also { rentRepository.makeRent(it) }

        bookRepository.updateBookStatus(bookId, BookStatus.READING)

        reservationRepository.delete(clientReservation)
    }

    @Transactional
    fun endRent(bookId: Long) {
        val clientRent = rentRepository.findByBookIdAndDueDateIsNotNull(bookId) ?: return

        clientRent.apply {
            softDelete()
            rentRepository.save(this)
        }

        bookRepository.updateBookStatus(bookId, BookStatus.AVAILABLE)
    }

    @Transactional(readOnly = true)
    fun getClientRentHistory(page: Int, userId: UUID) = pageRequest(page) {
        bookRepository.findRentedBooksByUserId(userId, it.pageSize, it.offset)
            .toDto(bookRepository.countRentedBooksByUserId(userId))
    }
}
