package ru.liljarn.booker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableJdbcRepositories
@EnableScheduling
@EnableAspectJAutoProxy
@EnableWebMvc
class BookerApplication

fun main(args: Array<String>) {
    runApplication<BookerApplication>(*args)
}
