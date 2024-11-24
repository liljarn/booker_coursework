package ru.liljarn.booker.support.mapper

import org.springframework.data.domain.Page
import ru.liljarn.booker.domain.model.dto.*
import ru.liljarn.booker.domain.model.entity.*
import ru.liljarn.gandalf.user.UserDataResponse
import java.util.*

fun Page<AuthorEntity>.toDto(): AuthorPage = AuthorPage(
    total = totalElements,
    authors = content.map { it.toDto() }
)

fun AuthorEntity.toDto(): Author = Author(
    authorId = authorId ?: throw RuntimeException(),
    authorName = authorName,
    photoUrl = authorPhotoUrl
)

inline fun Page<CommentEntity>.toDto(getUserData: (UUID) -> UserDataResponse): CommentPage = CommentPage(
    total = totalElements,
    comments = content.map { comment -> comment.toDto(getUserData.invoke(comment.userId)) }
)

fun CommentEntity.toDto(userData: UserDataResponse): Comment = Comment(
    commentId = commentId,
    userData = userData.toUserData(),
    comment = comment,
    rating = rating,
    bookId = bookId
)

inline fun CommentEntity.toDto(getUserData: (UUID) -> UserDataResponse): Comment = Comment(
    commentId = commentId,
    userData = getUserData.invoke(this.userId).toUserData(),
    comment = comment,
    rating = rating,
    bookId = bookId
)

fun List<Book>.toDto(total: Long): BookPage = BookPage(
    total = total,
    books = this
)

fun Page<GenreEntity>.toDto(): GenrePage = GenrePage (
    total = totalElements,
    genres = content.map { it.toDto() }
)

fun GenreEntity.toDto(): Genre = Genre(
    genreId = genreId,
    genreName = genreName,
)

fun ReservationEntity.toDto(): ClientReservation = ClientReservation(
    reservationId = reservationId,
    bookId = bookId,
    userId = userId,
    dueDate = dueDate
)
