package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.liljarn.booker.domain.model.dto.BookingInfo
import ru.liljarn.booker.domain.repository.BookRepository
import ru.liljarn.booker.domain.repository.RentRepository
import ru.liljarn.booker.domain.repository.ReservationRepository
import ru.liljarn.booker.support.mapper.toBook
import java.util.*

@Service
class BookingInfoService(
    private val bookRepository: BookRepository,
    private val rentRepository: RentRepository,
    private val reservationRepository: ReservationRepository
) {

    @Transactional
    fun getClientCurrentBookingInfo(userId: UUID) =
        findBookInfoWithReservation(userId) ?: findBookInfoWithRent(userId)

    private fun findBookInfoWithReservation(userId: UUID) =
        reservationRepository.findByUserId(userId)?.let {
            BookingInfo(
                book = bookRepository.findByBookId(it.bookId)!!.toBook(),
                dueDate = it.dueDate
            )
        }


    private fun findBookInfoWithRent(userId: UUID) =
        rentRepository.findByUserIdAndDeletedAtIsNull(userId)?.let {
            BookingInfo(
                book = bookRepository.findByBookId(it.bookId)!!.toBook(),
                dueDate = it.dueDate
            )
        }
}
