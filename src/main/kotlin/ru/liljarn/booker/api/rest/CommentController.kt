package ru.liljarn.booker.api.rest

import org.springframework.web.bind.annotation.*
import ru.liljarn.booker.api.model.request.CommentRequest
import ru.liljarn.booker.api.security.userContext
import ru.liljarn.booker.domain.service.CommentService
import java.util.*

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping("/{bookId}")
    fun getBookComments(@PathVariable bookId: Long, @RequestParam page: Int) =
        commentService.getBookComments(bookId, page)

    @GetMapping("/user")
    fun getUserComments(@RequestParam page: Int) = userContext {
        commentService.getUserComments(page)
    }

    @PostMapping("/{bookId}")
    fun addComment(@PathVariable bookId: Long, @RequestBody request: CommentRequest) = userContext {
        commentService.addComment(bookId, request)
    }

    @DeleteMapping
    fun deleteComment(@RequestParam commentId: UUID) = userContext {
        commentService.deleteComment(commentId)
    }
}
