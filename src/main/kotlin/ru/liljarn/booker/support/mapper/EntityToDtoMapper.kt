package ru.liljarn.booker.support.mapper

import ru.liljarn.booker.domain.model.dto.Author
import ru.liljarn.booker.domain.model.dto.Comment
import ru.liljarn.booker.domain.model.dto.Genre
import ru.liljarn.booker.domain.model.entity.AuthorEntity
import ru.liljarn.booker.domain.model.entity.CommentEntity
import ru.liljarn.booker.domain.model.entity.GenreEntity
import ru.liljarn.booker.support.security.userId
import ru.liljarn.gandalf.user.UserDataResponse
import java.util.*

fun AuthorEntity.toDto(): Author = Author(
    authorId = authorId ?: throw RuntimeException(),
    authorName = authorName,
    photoUrl = authorPhotoUrl
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

fun GenreEntity.toDto(): Genre = Genre(
    genreId = genreId ?: throw RuntimeException(),
    genreName = genreName,
)
