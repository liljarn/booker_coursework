package ru.liljarn.booker.infrastructure.minio

import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.stereotype.Component
import ru.liljarn.booker.infrastructure.support.properties.MinioClientProperties
import java.io.InputStream

@Component
class MinioImageComponent(
    private val minioClient: MinioClient,
    private val clientProperties: MinioClientProperties
) {

    fun uploadImage(file: InputStream, bucket: String, name: String) =
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(name)
                .stream(file, -1, 10485760)
                .contentType("image/jpeg")
                .build()
        ).let {
            getImageUrl(bucket, name)
        }


    fun getImageUrl(bucket: String, name: String) =
        "${clientProperties.url}/$bucket/$name"
}
