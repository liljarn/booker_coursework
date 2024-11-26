package ru.liljarn.booker.support.mapper

import ru.liljarn.booker.domain.model.dto.Book
import ru.liljarn.booker.domain.model.dto.BookDetailed
import ru.liljarn.booker.domain.model.dto.BookInfo
import ru.liljarn.booker.domain.model.dto.UserData
import java.util.*

fun BookInfo.toBook() = Book(
    bookId = bookId,
    bookName = bookName,
    authorId = authorId,
    authorName = authorName,
    authorPhotoUrl = authorPhotoUrl,
    releaseYear = releaseYear,
    ageLimit = ageLimit,
    description = description,
    photoUrl = photoUrl,
    rating = rating,
    status = status,
    genres = genres,
)

fun Book.toBookDetailed(userData: UserData?, userId: UUID?) = BookDetailed(
    book = this,
    userData = userData,
    self = userData?.userId == userId && userId != null
)
