package ru.liljarn.booker.support.mapper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.postgresql.util.PGobject
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.stereotype.Component
import ru.liljarn.booker.domain.model.dto.Genre

@Component
@ReadingConverter
class GenreReadingConverter(
    private val objectMapper: ObjectMapper
) : Converter<PGobject, ArrayList<Genre>> {
    override fun convert(pgObject: PGobject): ArrayList<Genre> =
        objectMapper.readValue(pgObject.value, object : TypeReference<ArrayList<Genre>>() {})
}
