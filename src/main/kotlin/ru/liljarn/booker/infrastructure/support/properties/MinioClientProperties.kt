package ru.liljarn.booker.infrastructure.support.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "minio", ignoreUnknownFields = false)
class MinioClientProperties {
    lateinit var url: String
    lateinit var accessKey: String
    lateinit var secretKey: String
    lateinit var authorBucket: String
    lateinit var bookBucket: String
}
