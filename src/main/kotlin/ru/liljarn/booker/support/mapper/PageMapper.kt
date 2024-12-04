package ru.liljarn.booker.support.mapper

import org.springframework.data.domain.Page
import ru.liljarn.booker.domain.model.dto.Book
import ru.liljarn.booker.domain.model.dto.BookHistory
import ru.liljarn.booker.domain.model.dto.BookManagement
import ru.liljarn.booker.domain.model.dto.page.*
import ru.liljarn.booker.domain.model.entity.AuthorEntity
import ru.liljarn.booker.domain.model.entity.CommentEntity
import ru.liljarn.gandalf.user.UserDataResponse
import java.util.*

fun Page<AuthorEntity>.toPage(): AuthorPage = AuthorPage(
    total = totalElements,
    authors = content.map { it.toDto() }
)

fun List<Book>.toPage(total: Long): BookPage = BookPage(
    total = total,
    books = this
)

fun List<BookManagement>.toPage(total: Long): BookManagementPage = BookManagementPage(
    total = total,
    managementBooks = this
)

inline fun Page<CommentEntity>.toPage(userId: UUID?, getUserData: (UUID) -> UserDataResponse): CommentPage = CommentPage(
    total = totalElements,
    comments = content.map { comment -> comment.toDto(userId, getUserData.invoke(comment.userId)) }
)

fun List<BookHistory>.toPage(total: Long) = BookHistoryPage(
    total = total,
    books = this
)