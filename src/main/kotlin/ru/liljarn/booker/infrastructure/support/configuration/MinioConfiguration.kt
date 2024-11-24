package ru.liljarn.booker.infrastructure.support.configuration

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.SetBucketPolicyArgs
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.liljarn.booker.infrastructure.support.properties.MinioClientProperties

@Configuration
class MinioConfiguration(
    private val properties: MinioClientProperties
) {

    @Bean
    fun minioClient(): MinioClient = MinioClient.builder()
        .endpoint(properties.url)
        .credentials(properties.accessKey, properties.secretKey)
        .build().apply {
            if (!bucketExists(BucketExistsArgs.builder().bucket(properties.bookBucket).build())) {
                makeBucket(MakeBucketArgs.builder().bucket(properties.bookBucket).build())
                setBucketPolicy(this, properties.bookBucket)
            }
            if (!bucketExists(BucketExistsArgs.builder().bucket(properties.authorBucket).build())) {
                makeBucket(MakeBucketArgs.builder().bucket(properties.authorBucket).build())
                setBucketPolicy(this, properties.authorBucket)
            }
        }

    private fun setBucketPolicy(minioClient: MinioClient, bucketName: String) {
        val policyJson = """
            {
                "Version": "2012-10-17",
                "Statement": [
                    {
                        "Effect": "Allow",
                        "Principal": "*",
                        "Action": "s3:GetObject",
                        "Resource": "arn:aws:s3:::$bucketName/*"
                    }
                ]
            }
        """.trimIndent()

        minioClient.setBucketPolicy(
            SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(policyJson)
                .build()
        )
    }
}
