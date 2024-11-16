package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import ru.liljarn.booker.api.model.request.CommentRequest
import ru.liljarn.booker.api.security.user
import ru.liljarn.booker.api.security.userId
import ru.liljarn.booker.domain.model.entity.CommentEntity
import ru.liljarn.booker.domain.repository.CommentRepository
import ru.liljarn.booker.infrastructure.grpc.GandalfService
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.pageRequest
import java.util.*

@Service
class CommentService(
    private val gandalfService: GandalfService,
    private val commentRepository: CommentRepository
) {
    fun getBookComments(bookId: Long, page: Int) = pageRequest(page) {
        commentRepository.findAllByBookId(it, bookId)
    }.toDto { uuid ->
        getUserData(uuid)
    }

    fun getUserComments(page: Int) = pageRequest(page) {
        commentRepository.findAllByUserId(it, user.userId)
    }.toDto { uuid ->
        getUserData(uuid)
    }

    fun addComment(bookId: Long, request: CommentRequest) = CommentEntity(
        commentId = UUID.randomUUID(),
        userId = user.userId,
        comment = request.comment,
        rating = request.rating,
        bookId = bookId
    ).apply {
        commentRepository.save(this)
    }.toDto { uuid ->
        getUserData(uuid)
    }

    fun deleteComment(commentId: UUID) = commentRepository.deleteById(commentId)

    private fun getUserData(uuid: UUID) = gandalfService.getUserById(uuid)
}
