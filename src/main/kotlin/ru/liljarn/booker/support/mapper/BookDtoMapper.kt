package ru.liljarn.booker.support.mapper

import ru.liljarn.booker.domain.model.dto.Book
import ru.liljarn.booker.domain.model.dto.BookDetailed
import ru.liljarn.booker.domain.model.dto.UserData
import java.util.*

fun Book.toBookDetailed(userData: UserData?, userId: UUID?) = BookDetailed(
    book = this,
    userData = userData,
    self = userData?.userId == userId && userId != null
)
