package ru.liljarn.booker.infrastructure.support.configuration

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import ru.liljarn.booker.infrastructure.support.properties.BookerKafkaProperties

@Configuration
class KafkaConfiguration(
    properties: BookerKafkaProperties,
) {

    private val defaultKafkaProperties = requireNotNull(properties.clusters["default"])

    @Primary
    @Bean
    fun defaultKafkaTemplate(): KafkaTemplate<String, Any> = KafkaTemplate(jsonProducerFactory(defaultKafkaProperties))

    private fun jsonProducerFactory(clusterProperties: KafkaProperties): ProducerFactory<String, Any> {
        val props = clusterProperties.buildProducerProperties(null)
        return DefaultKafkaProducerFactory(props)
    }
}
