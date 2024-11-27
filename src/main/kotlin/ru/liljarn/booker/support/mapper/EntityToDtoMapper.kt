package ru.liljarn.booker.support.mapper

import org.springframework.data.domain.Page
import ru.liljarn.booker.support.security.userId
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

inline fun Page<CommentEntity>.toDto(userId: UUID?, getUserData: (UUID) -> UserDataResponse): CommentPage = CommentPage(
    total = totalElements,
    comments = content.map { comment -> comment.toDto(userId, getUserData.invoke(comment.userId)) }
)

fun CommentEntity.toDto(userId: UUID?, userData: UserDataResponse): Comment = Comment(
    commentId = commentId,
    userData = userData.toUserData(),
    comment = comment,
    rating = rating,
    bookId = bookId,
    self = userData.userId == userId
)

inline fun CommentEntity.toDto(userId: UUID?, getUserData: (UUID) -> UserDataResponse): Comment =
    getUserData.invoke(this.userId).toUserData().let {
        Comment (
            commentId = commentId,
            userData = it,
            comment = comment,
            rating = rating,
            bookId = bookId,
            self = it.userId == userId
        )
    }

fun List<Book>.toDto(total: Long): BookPage = BookPage(
    total = total,
    books = this
)

fun GenreEntity.toDto(): Genre = Genre(
    genreId = genreId ?: throw RuntimeException(),
    genreName = genreName,
)

fun ReservationEntity.toDto(): ClientReservation = ClientReservation(
    reservationId = reservationId,
    bookId = bookId,
    userId = userId,
    dueDate = dueDate
)

fun List<BookManagement>.toPage(total: Long): BookManagementPage = BookManagementPage(
    total = total,
    managementBooks = this
)
