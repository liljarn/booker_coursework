package ru.liljarn.booker.api.model.request

import org.springframework.web.multipart.MultipartFile

data class AddBookRequest(
    val bookName: String,
    val releaseYear: String,
    val ageLimit: String,
    val description: String,
    val photo: MultipartFile,
    val genres: List<Int>
)
