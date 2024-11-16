package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import ru.liljarn.booker.api.security.user
import ru.liljarn.booker.api.security.userId
import ru.liljarn.booker.domain.model.entity.RentEntity
import ru.liljarn.booker.domain.repository.RentRepository
import java.time.LocalDate
import java.util.UUID

@Service
class RentService(
    private val rentRepository: RentRepository
) {

    fun rentBook(bookId: Long) = RentEntity(
        orderId = UUID.randomUUID(),
        bookId = bookId,
        userId = user.userId,
        dueDate = LocalDate.now().plusMonths(1),
    ).apply {
        rentRepository.save(this)
    }
}