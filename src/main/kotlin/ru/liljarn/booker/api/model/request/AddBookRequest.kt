package ru.liljarn.booker.api.model.request

import org.springframework.web.multipart.MultipartFile

data class AddBookRequest(
    val bookName: String,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val photo: MultipartFile,
    val genres: List<Int>
)
