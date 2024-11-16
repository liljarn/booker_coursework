package ru.liljarn.booker.domain.service

import org.springframework.stereotype.Service
import ru.liljarn.booker.domain.repository.AuthorRepository
import ru.liljarn.booker.support.mapper.toDto
import ru.liljarn.booker.support.pageRequest

@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {
    fun getAuthorById(authorId: Long) = authorRepository.findById(authorId).orElseThrow { RuntimeException() }.toDto()

    fun getAuthorsPage(page: Int) = pageRequest(page) {
        authorRepository.findAll(it)
    }.toDto()

    fun getAuthorsPageByName(page: Int, text: String) = pageRequest(page) {
        authorRepository.findAllByAuthorNameContainingIgnoreCase(text, it)
    }.toDto()
}
