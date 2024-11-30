package ru.liljarn.booker.domain.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.entity.RentEntity
import java.time.LocalDate
import java.util.*

@Repository
interface RentRepository : CrudRepository<RentEntity, UUID> {

    @Modifying
    @Query(
        """
        INSERT INTO book_rent_queue (rent_id, book_id, user_id, due_date)
        VALUES (:#{#rent.rentId}, :#{#rent.bookId}, :#{#rent.userId}, :#{#rent.dueDate})
        """
    )
    fun makeRent(@Param("rent") rentEntity: RentEntity)

    fun findByBookIdAndDeletedAtIsNull(bookId: Long): RentEntity?

    fun findByUserIdAndDeletedAtIsNull(userId: UUID): RentEntity?

    @Query(
        """
            SELECT * FROM book_rent_queue brq
            WHERE brq.due_date > :startDate
            AND brq.due_date <= :endDate
            AND brq.deleted_at IS NULL
        """
    )
    fun findByDueDateBetweenAndDeletedAtIsNull(startDate: LocalDate, endDate: LocalDate): List<RentEntity>

    @Query("""
            SELECT * FROM book_rent_queue brq
            WHERE brq.due_date <= :date
            AND brq.deleted_at IS NULL
    """)
    fun findByDueDateBeforeAndDeletedAtIsNull(date: LocalDate): List<RentEntity>
}
