package ru.liljarn.booker.support.mapper

import org.springframework.data.domain.Page
import ru.liljarn.booker.api.security.userId
import ru.liljarn.booker.domain.model.dto.*
import ru.liljarn.booker.domain.model.entity.AuthorEntity
import ru.liljarn.booker.domain.model.entity.CommentEntity
import ru.liljarn.gandalf.user.UserDataResponse
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

fun Page<AuthorEntity>.toDto(): AuthorPage = AuthorPage(
    total = totalElements,
    authors = content.map { it.toDto() }
)


fun AuthorEntity.toDto(): Author = Author(
    authorId = authorId,
    authorName = authorName,
    photoUrl = photoUrl
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

fun UserDataResponse.toUserData(): UserData = UserData(
    userId = userId,
    email = email,
    firstName = firstName,
    lastName = lastName,
    birthDate = Instant.ofEpochSecond(birthdate.seconds, birthdate.nanos.toLong()).atZone(ZoneOffset.UTC).toLocalDate(),
    photoUrl = photoUrl
)

fun List<Book>.toDto(total: Long): BookPage = BookPage(
    total = total,
    books = this
)
