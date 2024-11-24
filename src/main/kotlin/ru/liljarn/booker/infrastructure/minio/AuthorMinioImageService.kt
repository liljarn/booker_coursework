package ru.liljarn.booker.infrastructure.minio

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.liljarn.booker.domain.service.ImageService
import java.io.InputStream

@Service
class AuthorMinioImageService(
    @Value("\${minio.authorBucket}")
    private val bucket: String,
    private val minioImageComponent: MinioImageComponent
) : ImageService {

    override fun uploadImage(file: InputStream, name: String) =
        minioImageComponent.uploadImage(file, bucket, name)

    override fun getImageUrl(name: String) = minioImageComponent.getImageUrl(bucket, name)
}
