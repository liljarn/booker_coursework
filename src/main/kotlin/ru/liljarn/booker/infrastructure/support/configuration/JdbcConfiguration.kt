package ru.liljarn.booker.infrastructure.support.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import ru.liljarn.booker.support.mapper.GenreReadingConverter

@Configuration
class JdbcConfig(
    private val genreReadingConverter: GenreReadingConverter
) : AbstractJdbcConfiguration() {
    override fun userConverters(): List<Converter<*, *>> {
        return listOf(genreReadingConverter)
    }
}
