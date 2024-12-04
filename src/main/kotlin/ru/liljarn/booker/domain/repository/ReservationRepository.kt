package ru.liljarn.booker.domain.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.liljarn.booker.domain.model.dto.ReservationInfo
import ru.liljarn.booker.domain.model.entity.ReservationEntity
import java.time.LocalDate
import java.util.*

interface ReservationRepository : CrudRepository<ReservationEntity, UUID> {

    @Modifying
    @Query("""
        INSERT INTO book_reservation_queue (reservation_id, book_id, user_id, due_date)
        VALUES (:#{#req.reservationId}, :#{#req.bookId}, :#{#req.userId}, :#{#req.dueDate})
    """)
    fun makeReservation(@Param("req") reservationRequest: ReservationEntity)

    @Modifying
    @Query("""
        DELETE FROM book_reservation_queue brq
        WHERE due_date < :dueDate
    """)
    fun deleteOverdueReservations(@Param("dueDate") dueDate: LocalDate)

    @Query("""
        SELECT book_id FROM book_reservation_queue brq
        WHERE due_date < :dueDate
    """)
    fun findBookIdByDueDateBefore(@Param("dueDate") dueDate: LocalDate): List<Long>

    fun findByBookId(bookId: Long): ReservationEntity?

    fun findByUserId(userId: UUID): ReservationEntity?

    fun existsByUserId(userId: UUID): Boolean

    @Query("""
        SELECT b.book_id, brq.user_id, brq.due_date FROM book_reservation_queue brq
        RIGHT JOIN book b ON b.book_id = brq.book_id 
        WHERE b.book_id IN (:bookIds)
    """)
    fun findReservationInfo(bookIds: List<Long>): List<ReservationInfo>
}
