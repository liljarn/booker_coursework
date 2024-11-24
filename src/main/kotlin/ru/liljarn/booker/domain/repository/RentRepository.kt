package ru.liljarn.booker.domain.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljarn.booker.domain.model.entity.RentEntity
import java.util.*

@Repository
interface RentRepository : CrudRepository<RentEntity, UUID> {

    @Modifying
    @Query("""
        INSERT INTO book_rent_queue (rent_id, book_id, user_id, due_date)
        VALUES (:#{#rent.rentId}, :#{#rent.bookId}, :#{#rent.userId}, :#{#rent.dueDate})
        """)
    fun makeRent(@Param("rent") rentEntity: RentEntity)

    fun findByBookIdAndDueDateIsNotNull(bookId: Long): RentEntity?
}
