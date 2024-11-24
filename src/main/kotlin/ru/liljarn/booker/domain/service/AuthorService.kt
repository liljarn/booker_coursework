package ru.liljarn.booker.domain.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.liljarn.booker.api.model.request.AddAuthorRequest
import ru.liljarn.booker.domain.model.entity.AuthorEntity
import ru.liljarn.booker.domain.repository.AuthorRepository
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.pageRequest

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,

    @Qualifier("authorMinioImageService")
    private val imageService: ImageService
) {

    fun getAuthorById(authorId: Long) = authorRepository.findById(authorId).orElseThrow { RuntimeException() }.toDto()

    fun getAuthorsPage(page: Int) = pageRequest(page) {
        authorRepository.findAll(it)
    }.toDto()

    fun getAuthorsPageByName(page: Int, text: String) = pageRequest(page) {
        authorRepository.findAllByAuthorNameContainingIgnoreCase(text, it)
    }.toDto()

    fun addAuthor(request: AddAuthorRequest) {
        val authorPhotoUrl = request.authorPhoto?.let {
            imageService.uploadImage(it.inputStream, request.authorName)
        }

        AuthorEntity(
            authorName = request.authorName,
            authorPhotoUrl = authorPhotoUrl
        ).also { authorRepository.addNewAuthor(it) }
    }
}
