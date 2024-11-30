package ru.liljarn.booker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import ru.liljarn.booker.infrastructure.support.properties.BookerKafkaProperties
import ru.liljarn.booker.support.properties.InternalApiProperties
import ru.liljarn.booker.support.properties.ManagementApiProperties

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableJdbcRepositories
@EnableScheduling
@EnableAspectJAutoProxy
@EnableWebMvc
@EnableConfigurationProperties(
    BookerKafkaProperties::class,
    InternalApiProperties::class,
    ManagementApiProperties::class
)
class BookerApplication

fun main(args: Array<String>) {
    runApplication<BookerApplication>(*args)
}
